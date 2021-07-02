package com.rizq.android.demo.data.server

import com.google.gson.*
import com.rizq.android.domain.core.*
import org.json.*
import java.io.StringReader

class JsonServerParser {

  inline fun <reified T> genericParseDecodedString(stringDecoded: String): Either<Failure, T> = try {
    Either.Right(Gson().fromJson(StringReader(stringDecoded), T::class.java))
  } catch (e: JsonSyntaxException) {
    Either.Left(Failure.JsonException(e.message))
  }

  inline fun <reified T> specificParseDecodedString(stringDecoded: String): T = Gson().fromJson(StringReader(stringDecoded), T::class.java)

  inline fun <reified T> parseArrayResponse(jsonString: String): List<T> {
    val genericListType: MutableList<T> = mutableListOf()
    try {
      val json = JSONArray(jsonString)
      for (i in 0 until json.length()) try {
        genericListType += specificParseDecodedString<T>(json[i].toString())
      } catch (e: JsonSyntaxException) {
        e.printStackTrace()
      }
    } catch (e: JsonParseException) {
    }
    return genericListType
  }

  fun parseDecodeJsonObjectFromArray(stringDecoded: String, index: Int): Either<Failure, JSONObject> = try {
    Either.Right(JSONArray(stringDecoded).getJSONObject(index))
  } catch (e: JSONException) {
    Either.Left(Failure.JsonException(e.message))
  }

  fun parseErrorBody(errorBody: Failure.ErrorBody): Failure =
    if (errorBody.body.isNullOrEmpty() || errorBody.body == "[]") Failure.ErrorBody(errorBody.requestCode, null)
    else when (val value = parseDecodeJsonObjectFromArray(errorBody.body!!, 0)) {
      is Either.Left -> value.a
      else -> Failure.ErrorBody(errorBody.requestCode, errorBody.body)
    }

  fun parseErrorBodyArray(errorBody: Failure.ErrorBody): Failure =
    if (errorBody.body.isNullOrEmpty() || errorBody.body == "[]") Failure.ErrorBody(errorBody.requestCode, null)
    else {
      val errorBodyAux = "[${errorBody.body!!}]"
      when (val value = parseDecodeJsonObjectFromArray(errorBodyAux, 0)) {
        is Either.Left -> value.a
        else -> Failure.ErrorBody(errorBody.requestCode, errorBody.body)
      }
    }
}