package com.rizq.android.domain.models.local

interface LocalObjectInterface

interface FutureObjectWithImage : LocalObjectInterface{
  abstract val imageURL: String
}