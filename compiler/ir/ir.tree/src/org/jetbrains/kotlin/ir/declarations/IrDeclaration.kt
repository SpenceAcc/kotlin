/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.ir.declarations

import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.symbols.IrSymbol
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer
import org.jetbrains.kotlin.name.Name

interface IrSymbolOwner : IrElement {
    val symbol: IrSymbol
}

interface IrMetadataSourceOwner : IrElement {
    val metadata: MetadataSource?
}

interface IrDeclaration : IrStatement, IrAnnotationContainer, IrMetadataSourceOwner {
    val descriptor: DeclarationDescriptor
    var origin: IrDeclarationOrigin

    var parent: IrDeclarationParent

    override fun <D> transform(transformer: IrElementTransformer<D>, data: D): IrStatement =
        accept(transformer, data) as IrStatement
}

interface IrSymbolDeclaration<out S : IrSymbol> : IrDeclaration, IrSymbolOwner {
    override val symbol: S
}

interface IrOverridableDeclaration<S : IrSymbol> : IrDeclaration {
    val overriddenSymbols: MutableList<S>
}

interface IrDeclarationWithVisibility : IrDeclaration {
    val visibility: Visibility
}

interface IrDeclarationWithName : IrDeclaration {
    val name: Name
}

