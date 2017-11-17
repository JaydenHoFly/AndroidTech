package com.jaydenho.androidtech.kotlin.test

import com.jaydenho.androidtech.test.Test

/**
 * Created by hedazhao on 2017/11/2.
 */

fun a() {}

const val a1 = 1

class Test01 {
    private val log: Int? = null
    private val log1 = "test $log"
    val log2: String = ""
    var log3 = ""

    fun learnVar(a: String, b: Int): String? {
        //call java
        val t = Test()
        t.Father()
        log3 = "${a.replace("is", "was")} $b"
        return null
    }

    fun learnNull(a: String, b: String?): String? {
        var nullableList: List<Int?> = listOf(1, 2, null, 4)
        nullableList = nullableList.filter {
            false
        }
        var d: String = ""
        var e = d as? Int
        b?.length
        b!!.length
        val aInt: String? = learnVar(a, 1)
        val bInt = b
        if (aInt != null) {
            println(a)
        }
        val c: String? = ""
        c?.length
        val listWithNulls: List<String?> = listOf("A", null)
        for (item in listWithNulls) {
            item?.let { println(it) } // prints A and ignores null
            val a: Int? = item?.let {
                it.length
            }
            item.let {
                println(it)
            }
            val c = item?.length ?: throw NullPointerException()
        }
        return ""
    }

    open class A(a: String) {
        constructor(a: String, b: String) : this(a)

        open var a = ""

        protected open fun a() {
            c(b = 3)
            c(a = 3)
            c(4, 5)
            c(c = "1")
        }

        open fun c(a: Int = 1, b: Int = 2, c: String = "") {}

    }

    class B : A {

        override var a: String
            get() = this.a
            set(value) {
                a = value
            }

        val b: String
            get() = "${AB} a"


        constructor(a: String) : super(a) {
            this.a
        }

        constructor(a: String, b: String) : super(a)

        public override fun a() {
            super.a()
            c({ a.length })
        }

        fun c(function: (a: String) -> Int) {

        }

        companion object {
            const val AB = "ab"
        }

        //singleton
        object A {
            fun bb() {

            }
        }

        //        Local Function
        fun aa() {
            fun bb() {

            }
            bb()
        }
    }

    // interface
    interface IA {
        val a: Int
            get() = 1

        var b: Int

        fun a()

        fun b() {

        }
    }

    class AI : IA {
        override var a: Int = 0
        override var b: Int = 0
        override fun a() {
        }

        //when
        fun c(a: Int) {
            when (a) {
                1 -> "a"
                in 2..4 -> "b"
                !in 5..6 -> "c"
                else -> if (a < 0) "d" else "e"
            }

            fun d(a: Int): Boolean {
                return a > 0
            }

            when {
                a > 0 -> "a"
                else -> "d"
            }

        }

    }

    //molti inherit
    interface IAA {
        fun a()
        fun b() {

        }
    }

    interface IBB {
        fun a() {

        }

        fun b() {

        }
    }

    interface ICC : IAA, IBB {
        override fun a() {
            super.a()
        }

        override fun b() {
            super<IBB>.b()
        }

    }

}