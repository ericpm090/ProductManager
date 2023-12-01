package com.example.productmanager.ui.admin.ui.incidences

import com.example.productmanager.domain.model.Incidence

class IncidencesProvider {

    companion object {
        val list = listOf<Incidence>(
            Incidence(
                "1",
                "Paco",
                "01-05-2023",
                "1",
                "Alicates",
                "Mango roto"

            ),
            Incidence(
                "2",
                "Manolo",
                "01-05-2023",
                "2",
                "Pinzas",
                "Punta rota"

            ),
            Incidence(
                "3",
                "Alex",
                "01-05-2023",
                "3",
                "Soldador",
                "Punta rota"

            )

        )
    }
}