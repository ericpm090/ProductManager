package com.example.productmanager.domain.model

enum class ToolType(val code:String) {
    TOOL("1"),
    INSTRUMENT("2"),
    CONSUMABLE("3"),
    SYSTEM_OTHERS("4"),
    BOX("5");

    companion object {
        fun getCodeByType(type: String): String {

            return values().find { it.name == type }?.code ?: "Unknown Value"
        }

        fun getAllTypes():MutableList<String>{
            val values_list = ArrayList<ToolType>(ToolType.values().asList())
            val list = mutableListOf<String>()

            for(value in values_list){
                list.add(value.name)
            }

            return list
        }
    }

}