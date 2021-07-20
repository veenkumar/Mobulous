package com.veen.mobulous.model

data class SearchResponse(
    val restaurants: List<Restaurant>,
    val results_found: String,
    val results_shown: String,
    val results_start: String
)