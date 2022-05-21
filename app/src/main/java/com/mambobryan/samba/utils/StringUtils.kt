package com.mambobryan.samba.utils

import java.text.SimpleDateFormat
import java.util.*

fun String?.toDate(): Date? {

    if (this == null || this.isBlank() || this.isEmpty()) throw NullPointerException()

    val simpleFormat = SimpleDateFormat("yyy-mm-d'T'hh:mm:ss");

    return this.let { simpleFormat.parse(it) }

}