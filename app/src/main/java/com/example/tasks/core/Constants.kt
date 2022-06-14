package com.example.tasks.core

/**
 * Application Constants
 */
class Constants private constructor() {
    // Database
    object DATABASE {
        const val NAME = "tasksDB"
        const val VERSION = 1

        object TABLES {
            const val PRIORITY = "priority"
        }
    }

    // SharedPreferences
    object SHAREDPREFERENCES {
        const val NAME = "taskShared"

        object KEYS {
            const val TOKEN_KEY = "tokenkey"
            const val PERSON_KEY = "personkey"
            const val PERSON_NAME = "personname"
        }
    }

    // API Requests
    object API {
        const val BASE_URL = "http://devmasterteam.com/cursoandroidapi/"

        // Header keys
        object HEADER {
            const val TOKEN_KEY = "token"
            const val PERSON_KEY = "personkey"
        }

        // HTTP codes
        object HTTP {
            const val SUCCESS = 200
        }
    }

    object BUNDLE {
        const val TASKID = "taskid"
        const val TASKFILTER = "taskfilter"
    }

    object FILTER {
        const val ALL = 0
        const val NEXT = 1
        const val EXPIRED = 2
    }
}