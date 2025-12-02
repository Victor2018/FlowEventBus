package com.cherry.floweventbus

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UserInfo
 * Author: Victor
 * Date: 2025/12/2 9:03
 * Description: 
 * -----------------------------------------------------------------
 */

class UserInfo {
    var name: String? = null
    var sex: String? = null
    var mail: String? = null
    var address: String? = null

    override fun toString(): String {
        return "{\"name\":$name" +
                "\"sex\":$sex" +
                "\"mail\":$mail" +
                "\"address\":$address}"
    }
}