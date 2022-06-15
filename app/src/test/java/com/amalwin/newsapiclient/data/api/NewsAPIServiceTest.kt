package com.amalwin.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsAPIService::class.java)
    }

    fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadLines_requestSent_requestExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadLines("us", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=f736463add6a4f10ac20c7e466b6f2d4")
        }
    }

    @Test
    fun getTopHeadLines_responseReceived_checkResponseSize() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadLines("us", 1).body()
            val articles = responseBody!!.articles
            assertThat(articles.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadLines_responseReceived_checkResponseContent() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadLines("us", 1).body()
            val articles = responseBody!!.articles
            val article = articles[0]
            assertThat(article.author).isEqualTo("William Suberg")
            assertThat(article.source.name).isEqualTo("Cointelegraph")
            assertThat(article.title).isEqualTo("BTC price crashes to \$20.8K as ‘deadly’ candles liquidate \$1.2 billion - Cointelegraph")
            assertThat(article.url).isEqualTo("https://cointelegraph.com/news/btc-price-crashes-to-20-8k-as-deadly-candles-liquidate-1-2-billion")
            assertThat(article.publishedAt).isEqualTo("2022-06-14T06:32:43Z")
        }

    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}