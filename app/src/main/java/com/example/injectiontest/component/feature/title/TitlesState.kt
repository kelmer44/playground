package com.example.injectiontest.component.feature.title

data class TitlesState(
    val whatsPlaying: String,
    val titleData: TitleData?,
    val subtitleData: SubtitleData?,
) {
    /**
     * Data used by Presenter to resolve the Title text.
     */
    data class TitleData(
        val title: CharSequence?,
        val contentDescription: CharSequence? = null,
        val episodeData: EpisodeData? = null
    ) {

        /**
         * Additional data for Episodes.
         */
        data class EpisodeData(
            val isStudioShow: Boolean = false,
            val seasonNumber: Int,
            val episodeNumber: Int?,
        )
    }

    /**
     * Data used by Presenter to resolve the Subtitle text.
     */
    data class SubtitleData(
        val subTitle: CharSequence?,
        val contentDescription: CharSequence? = null
    )
}
