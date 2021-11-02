package com.example.injectiontest

import java.util.Objects


fun Any.hexCode(): String =
    Integer.toHexString(hashCode())
