/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.fir.symbols

import org.jetbrains.kotlin.analysis.api.KaInitializerValue
import org.jetbrains.kotlin.analysis.api.base.KaContextReceiver
import org.jetbrains.kotlin.analysis.api.fir.KaSymbolByFirBuilder
import org.jetbrains.kotlin.analysis.api.fir.utils.asKaInitializerValue
import org.jetbrains.kotlin.analysis.api.impl.base.KaBaseContextReceiver
import org.jetbrains.kotlin.analysis.api.impl.base.symbols.asKaSymbolModality
import org.jetbrains.kotlin.analysis.api.platform.resolution.KaResolutionActivityTracker
import org.jetbrains.kotlin.analysis.api.symbols.KaContextParameterSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KaSymbolModality
import org.jetbrains.kotlin.analysis.api.symbols.KaTypeParameterSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KaValueParameterSymbol
import org.jetbrains.kotlin.analysis.api.types.KaType
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.utils.modality
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression
import org.jetbrains.kotlin.fir.references.impl.FirPropertyFromParameterResolvedNamedReference
import org.jetbrains.kotlin.fir.resolve.toRegularClassSymbol
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol
import org.jetbrains.kotlin.fir.symbols.impl.*
import org.jetbrains.kotlin.fir.types.ConeDynamicType
import org.jetbrains.kotlin.fir.types.create
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.isLocal

internal fun FirFunctionSymbol<*>.createKtValueParameters(builder: KaSymbolByFirBuilder): List<KaValueParameterSymbol> {
    return fir.valueParameters.map { valueParameter ->
        builder.variableBuilder.buildValueParameterSymbol(valueParameter.symbol)
    }
}

internal fun FirCallableSymbol<*>.createKaContextParameters(builder: KaSymbolByFirBuilder): List<KaContextParameterSymbol> {
    return fir.contextParameters.map { contextParameter ->
        builder.variableBuilder.buildContextParameterSymbol(contextParameter.symbol)
    }
}

internal fun <D> FirBasedSymbol<D>.createKtTypeParameters(
    builder: KaSymbolByFirBuilder
): List<KaTypeParameterSymbol> where D : FirTypeParameterRefsOwner, D : FirDeclaration {
    return fir.typeParameters.map { typeParameter ->
        builder.classifierBuilder.buildTypeParameterSymbol(typeParameter.symbol)
    }
}

internal fun <D> FirBasedSymbol<D>.createRegularKtTypeParameters(
    builder: KaSymbolByFirBuilder,
): List<KaTypeParameterSymbol> where D : FirTypeParameterRefsOwner, D : FirDeclaration {
    return fir.typeParameters.filterIsInstance<FirTypeParameter>().map { typeParameter ->
        builder.classifierBuilder.buildTypeParameterSymbol(typeParameter.symbol)
    }
}

internal fun FirCallableSymbol<*>.createContextReceivers(
    builder: KaSymbolByFirBuilder
): List<KaContextReceiver> {
    return contextParameterSymbols.map { createContextReceiver(builder, it) }
}

internal fun FirRegularClassSymbol.createContextReceivers(
    builder: KaSymbolByFirBuilder
): List<KaContextReceiver> {
    return resolvedContextParameters.map { createContextReceiver(builder, it.symbol) }
}

private fun createContextReceiver(
    builder: KaSymbolByFirBuilder,
    contextParameter: FirValueParameterSymbol
) = KaBaseContextReceiver(
    builder.typeBuilder.buildKtType(contextParameter.resolvedReturnType),
    contextParameter.name,
    builder.token
)

internal fun FirCallableSymbol<*>.getCallableId(): CallableId? {
    return when {
        origin == FirDeclarationOrigin.DynamicScope -> null
        callableId.isLocal -> null
        else -> callableId
    }
}

internal fun FirClassLikeSymbol<*>.getClassId(): ClassId? =
    classId.takeUnless { it.isLocal }

internal fun FirCallableSymbol<*>.dispatchReceiverType(
    builder: KaSymbolByFirBuilder,
): KaType? {
    val type = if (
        origin == FirDeclarationOrigin.DynamicScope
        && (this is FirPropertySymbol || this is FirFunctionSymbol)
    ) {
        ConeDynamicType.create(builder.rootSession)
    } else {
        dispatchReceiverType
    }
    return type?.let { builder.typeBuilder.buildKtType(it) }
}

internal fun FirVariableSymbol<*>.getKtConstantInitializer(builder: KaSymbolByFirBuilder): KaInitializerValue? {
    // to avoid lazy resolve
    if (fir.initializer == null) return null

    var firInitializer = resolvedInitializer ?: return null
    if (firInitializer is FirPropertyAccessExpression) {
        val calleeReference = firInitializer.calleeReference
        if (calleeReference is FirPropertyFromParameterResolvedNamedReference) {
            val valueParameterSymbol = calleeReference.resolvedSymbol as? FirValueParameterSymbol
            if (valueParameterSymbol != null) {
                firInitializer = valueParameterSymbol.resolvedDefaultValue ?: firInitializer
            }
        }
    }

    val parentIsAnnotation = dispatchReceiverType
        ?.toRegularClassSymbol(builder.rootSession)
        ?.classKind == ClassKind.ANNOTATION_CLASS

    return firInitializer.asKaInitializerValue(builder, parentIsAnnotation)
}

internal val FirBasedSymbol<*>.kaSymbolModality: KaSymbolModality
    get() = when (this) {
        is FirCallableSymbol<*> -> modality
        is FirClassLikeSymbol<*> -> modality
        else -> Modality.FINAL
    }.asKaSymbolModality

/**
 * Issue: KT-58572
 *
 * Visibility computation can be requested from Java resolution via light classes,
 * so [resolvedStatus][FirClassLikeSymbol.resolvedStatus] can be called only if there is no pending resolution
 * on the stack.
 * Otherwise, only [rawStatus][FirClassLikeSymbol.rawStatus] can be requested in this case to avoid
 * potential contract violation.
 * However, the status still might be already resolved at this moment,
 * so the resolved status still might be returned in this case.
 */
internal val FirClassLikeSymbol<*>.possiblyRawVisibility: Visibility
    get() = if (KaResolutionActivityTracker.getInstance()?.isKotlinResolutionActive == true) {
        rawStatus
    } else {
        resolvedStatus
    }.visibility
