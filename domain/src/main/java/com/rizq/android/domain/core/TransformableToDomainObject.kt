package com.rizq.android.domain.core

import com.rizq.android.domain.models.local.LocalObjectInterface

interface TransformableToDomainObject {
    abstract fun toDomain(): LocalObjectInterface
}