package com.rizq.android.domain.core

import kotlinx.coroutines.flow.flow

sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRht get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    suspend fun fold(fnL: suspend (L) -> Any, fnR: suspend (R) -> Any): Any = when (this) {
        is Left -> fnL(a)
        is Right -> fnR(b)
    }
}

suspend fun <A, B, C> (suspend (A) -> B).c(f: suspend (B) -> C): suspend (A) -> C = {
    f(this(it))
}

suspend fun <T, L, R> Either<L, R>.flatMapToRight(fn: suspend (R) -> Either<L, T>): Either<L, T> = when (this) {
    is Either.Left -> Either.Left(a)
    is Either.Right -> fn(b)
}

suspend fun <T : Failure, L, R> Either<L, R>.flatMapToLeft(fn: suspend (L) -> Either<T, R>): Either<T, R> = when (this) {
    is Either.Left -> fn(a)
    is Either.Right -> Either.Right(b)
}

suspend fun <T, L, R> Either<L, R>.mapToRight(fn: suspend (R) -> (T)): Either<L, T> = this.flatMapToRight(fn.c(::right))

fun <L, R> Either<L, R>.asFlow() = flow { emit(this@asFlow) }