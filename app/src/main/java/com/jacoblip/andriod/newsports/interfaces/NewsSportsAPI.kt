package com.jacoblip.andriod.newsports.interfaces


import androidx.lifecycle.LiveData
import com.jacoblip.andriod.newsports.data.models.VideoHighlight
import com.jacoblip.andriod.newsports.data.models.callbacks.*

import com.jacoblip.andriod.newsports.utilities.limit
import com.jacoblip.andriod.newsports.utilities.playerLimit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

const val fixtures = "fixtures.localTeam,fixtures.visitorTeam,fixtures.league,fixtures.league.country"
const val upcoming = "upcoming.localTeam,upcoming.visitorTeam,upcoming.league,upcoming.league.country"
const val latest = "latest.localTeam,latest.visitorTeam,latest.league,latest.league.country"
const val results = "results.localTeam,results.visitorTeam,results.league,results.league.country"
private const val localResults = "localResults.localTeam,localResults.visitorTeam,localResults.league,localResults.league.country"
private const val visitorResults = "visitorResults.localTeam,visitorResults.visitorTeam,visitorResults.league,visitorResults.league.country"
private const val visitorFixtures = "visitorFixtures.localTeam,visitorFixtures.visitorTeam,visitorFixtures.league,visitorFixtures.league.country"
private const val localFixtures = "localFixtures.localTeam,localFixtures.visitorTeam,localFixtures.league,localFixtures.league.country"

interface NewSportsAPI {
    @GET("standings/season/{id}?&include=standings.team")
    fun standings(@Path("id") seasonId: Long,@Query("api_token")api:String): Call<StandingsCallback>

   // @GET("livescores?api_token=$API_KEY&include=localTeam,visitorTeam")
   // fun livescores(): Call<MatchesCallback>

    @GET("livescores/now?&include=localTeam,visitorTeam,league,league.country")
    fun livescoresNow(@Query("api_token")api:String): Call<MatchesCallback>

    @GET("leagues?&include=season.stage,season.round,country:order(code|desc)")
    fun leagues(@Query("api_token")api:String): Call<LeaguesCallback>

   // @GET("leagues?api_token=$API_KEY&include=season.stage,season.round,country:order(code|desc)")
   // fun leaguesLiveData(): LiveData<ApiResponse<CompetitionsCallback>>

   // @GET("fixtures/multi/{ids}?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country,round")
   // fun fixturesByIdLive(@Path("ids") fixtureIds: String): LiveData<ApiResponse<MatchesCallback>>

  //  @GET("teams/season/{seasonId}?api_token=$API_KEY")
  //  fun seasonTeams(@Path("seasonId") seasonId: Long): Call<TeamsCallback>

    @GET
    fun seasonMatches(@Url() url: String): Call<SeasonMatchesCallback>

   // @GET("seasons/{id}?api_token=$API_KEY&include=upcoming$limit,$upcoming")
  //  fun seasonUpcoming(@Path("id") seasonId: Long): Call<SeasonUpcomingCallback>

  //  @GET("seasons/{id}?api_token=$API_KEY&include=fixtures$limit,$fixtures")
  //  fun seasonFixtures(@Path("id") seasonId: Long): Call<SeasonFixturesCallback>

   // @GET("seasons/{id}?api_token=$API_KEY&include=results$limit,$results")
   // fun seasonResults(@Path("id") seasonId: Long): Call<SeasonResultsCallback>

  //  @GET("squad/season/{seasonId}/team/{teamId}?api_token=$API_KEY&include=fixtures")
  //  fun seasonSquad(@Path("seasonId") seasonId: Long, @Path("teamId") teamId: Long): Call<SeasonTopScorersCallback>

    @GET("topscorers/season/{seasonId}?&include=goalscorers$playerLimit:order(goals|desc),goalscorers.player,assistscorers$playerLimit:order(assists|desc),assistscorers.player,cardscorers$playerLimit:order(yellowcards|desc),cardscorers.player,goalscorers.team,cardscorers.team,assistscorers.team")
    fun seasonTopPlayers(@Path("seasonId") seasonId: Long,@Query("api_token")api:String): Call<SeasonTopScorersCallback>

  //  @GET("leagues/{id}?api_token=$API_KEY")
  //  fun leagueById(@Path("id") leagueId: Long): Call<LeagueCallback>

  //  @GET("teams/{id}?api_token=$API_KEY")
   // fun teamById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("teams/{id}?&include=visitorResults$limit,$visitorResults,localResults$limit,$localResults")
    fun teamResultsById(@Path("id") leagueId: Long,@Query("api_token")api:String): Call<TeamCallback>

    @GET("teams/{id}?&include=visitorFixtures$limit,$visitorFixtures,localFixtures$limit,$localFixtures,upcoming$limit,$upcoming,latest$limit,$latest")
    fun teamFixturesById(@Path("id") leagueId: Long,@Query("api_token")api:String): Call<TeamCallback>

    @GET("teams/{id}?&include=squad.player,squad.position")
    fun teamSquadById(@Path("id") leagueId: Long,@Query("api_token")api:String): Call<TeamCallback>

    @GET("teams/{id}?&include=venue,country,coach,uefaranking")
    fun teamOverviewById(@Path("id") leagueId: Long,@Query("api_token")api:String): Call<TeamCallback>

  //  @GET("teams/{id}?api_token=$API_KEY&include=transfers.player")
  //  fun teamTransfersById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("countries?&include=leagues")
    fun countries(@Query("api_token")api:String): Call<CountriesCallback>

    @GET("fixtures/{id}/?&include=localTeam,visitorTeam,goals,cards,substitutions,lineup,stats,league,league.country,round")
    fun fixturesById(@Path("id") fixtureId: Long,@Query("api_token")api:String): Call<MatchCallback>

    @GET("fixtures/between/{from}/{to}?&include=localTeam,visitorTeam,league,league.country")
    fun fixturesBetweenDates(@Path("from") todayDate: String, @Path("to") toDate: String,@Query("api_token")api:String): Call<MatchesCallback>

    @GET("fixtures/date/{date}?&include=localTeam,visitorTeam,league,league.country,round")
    fun fixturesForDate(@Path("date") todayDate: String,@Query("api_token")api:String): Call<MatchesCallback>

   // @GET("fixtures/multi/{ids}?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country,round")
  //  fun particularFixtures(@Path("ids") fixtureIds: String): Call<MatchesCallback>

    @GET("commentaries/fixture/{id}?")
    fun commentary(@Path("id") fixtureId: Long,@Query("api_token")api:String): Call<CommentariesCallback>

  //  @GET("odds/fixture/{id}/bookmaker/{bookmaker}?api_token=$API_KEY")
  //  fun fixtureBookmakerOdds(
  //      @Path("id") fixtureId: Long,
  //      @Path("bookmaker") bookmaker: Long
  //  ): Call<OddsCallback>

    @GET("?players/{id}?")
    fun player(@Path("id") playerId: Long,@Query("api_token")api:String): Call<PlayerCallback>

    @GET("head2head/{team1id}/{team2id}?&include=localTeam,visitorTeam")
    fun getH2H(@Path("team1id") team1id: Long, @Path("team2id") team2id: Long,@Query("api_token")api:String): Call<MatchesCallback>

  //  @GET("v1")
  //  fun videos(): Call<ArrayList<VideoHighlight>>

  //  @GET("news")
  //  fun news(): Call<NewsCallback>

  //  @GET("news/{id}/{slug}")
  //  fun singleNews(@Path("id") news_id: String, @Path("slug") slug: String): Call<SingleNewsCallback>
}