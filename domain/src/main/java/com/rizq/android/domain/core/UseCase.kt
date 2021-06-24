package com.rizq.android.domain.core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class UseCaseFlow<Params, Return> {
    suspend operator fun invoke(params: Params): Flow<Either<Failure, Return>> = execute(params).flowOn(Dispatchers.IO)
    protected abstract suspend fun execute(params: Params): Flow<Either<Failure, Return>>
}

abstract class UseCase<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Either<Failure, Type>
    suspend fun execute(params: Params): Either<Failure, Type> = run(params)
}

abstract class SimpleUseCase<out Type, in Params> where Type : Any {
    abstract fun run(params: Params): Type
    suspend fun execute(params: Params): Type = run(params)
}