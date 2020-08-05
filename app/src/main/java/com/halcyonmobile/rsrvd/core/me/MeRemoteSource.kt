
class MeRemoteSource {
    private val meApi = RetrofitManager.retrofit.create(MeApi::class.java)

    fun update(location: Location, interests: List<Interests>, updateState: (Boolean) -> Unit) =
        meApi.update(ProfileDto(UserRepository.name, location, interests)).enqueue(ProfileUpdateHandler(updateState))
}