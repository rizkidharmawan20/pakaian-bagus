package com.example.pakaianbagus.api;

import com.example.pakaianbagus.models.AnnouncementResponse;
import com.example.pakaianbagus.models.ApiResponse;
import com.example.pakaianbagus.models.BrandResponse;
import com.example.pakaianbagus.models.ChecklistResponse;
import com.example.pakaianbagus.models.Discount;
import com.example.pakaianbagus.models.Kompetitor;
import com.example.pakaianbagus.models.LoginRequest;
import com.example.pakaianbagus.models.PenerimaanBarangResponse;
import com.example.pakaianbagus.models.PenjualanResponse;
import com.example.pakaianbagus.models.SalesReport;
import com.example.pakaianbagus.models.StockOpnameModel;
import com.example.pakaianbagus.models.TokoResponse;
import com.example.pakaianbagus.models.User;
import com.example.pakaianbagus.models.api.penjualankompetitor.KompetitorResponse;
import com.example.pakaianbagus.models.api.salesreport.SalesReportResponse;
import com.example.pakaianbagus.models.auth.Auth;
import com.example.pakaianbagus.models.stock.Category;
import com.example.pakaianbagus.models.stock.Item;
import com.example.pakaianbagus.models.stock.Stock;
import com.example.pakaianbagus.models.user.Checklist;

import java.lang.ref.Reference;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://37.72.172.144/pb-v2/public/api/";
    //String BASE_URL = "http://37.72.172.144/pb/pakaianbagus-api/";
    //String BASE_URL = "http://37.72.172.144/pb-v1/public/";
    //String BASE_URL = "http://37.72.172.144/pb-v1/";


    @POST("auth/login")
    Call<Auth> postLogin(@Body LoginRequest loginRequest);

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @GET("checklists")
    Call<ApiResponse<List<ChecklistResponse>>> getRoleChecklist(@Header("Authorization") String token,
                                                                @Query("role_id") String role_id);

    @GET("places")
    Call<ApiResponse<List<TokoResponse>>> getListToko(@Header("Authorization") String token,
                                                      @Query("type") String type);

    @GET("brands")
    Call<ApiResponse<List<BrandResponse>>> getListBrand(@Header("Authorization") String token);

    @GET("stocks")
    Call<ApiResponse<List<Stock>>> getListStokToko(@Header("Authorization") String token,
                                                   @Query("id_store") String id_store,
                                                   @Query("id_brand") String idBrand,
                                                   @Query("limit") int limit,
                                                   @Query("offset") int offset);

    @GET("stok_toko/getAllArtikel")
    Call<ApiResponse<List<Stock>>> searchKatalog(@Header("Authorization") String token,
                                                 @Query("id_store") String id_store,
                                                 @Query("is_exist") String is_exist,
                                                 @Query("is_vip") String is_vip,
                                                 @Query("keyword") String keyword,
                                                 @Query("limit") int limit,
                                                 @Query("offset") int offset,
                                                 @Query("order_by") String order_by,
                                                 @Query("order_type") String order_type);

    @GET("stok_toko/getAllArtikelWithStokToko")
    Call<ApiResponse<List<Stock>>> searchBarangPenjualan(@Header("Authorization") String token,
                                                         @Query("keyword") String keyword);

    @GET("users")
    Call<ApiResponse<List<User>>> getListKoordinator(@Header("Authorization") String token,
                                                     @Query("role_id") String role_id);

    @GET("penerimaan/all")
    Call<ApiResponse<List<PenerimaanBarangResponse>>> getListBarangMasuk(@Header("Authorization") String token,
                                                                         @Query("id_store") String id_store,
                                                                         @Query("limit") int limit,
                                                                         @Query("offset") int offset);

    @GET("penerimaan/byid/{id}")
    Call<ApiResponse<PenerimaanBarangResponse>> getDetailBarangMasuk(@Header("Authorization") String token,
                                                                     @Path("id") String id);

    @POST("attendances/checkIn")
    Call<ApiResponse> postCheckIn(@Header("Authorization") String token,
                                  @Body RequestBody checkInData);

    @POST("attendances/checkOut")
    Call<ApiResponse> postCheckOut(@Header("Authorization") String token,
                                   @Body RequestBody checkInData);

    @GET("announcements")
    Call<ApiResponse<List<AnnouncementResponse>>> getAnnouncement(@Header("Authorization") String token);

    @GET("pos/history")
    Call<ApiResponse<List<PenjualanResponse>>> getPenjualan(@Header("Authorization") String token,
                                                            @Query("id_store") String id_store,
                                                            @Query("is_vip") Boolean is_vip,
                                                            @Query("limit") int limit,
                                                            @Query("offset") int offset,
                                                            @Query("order_by") String order_by,
                                                            @Query("order_type") String order_type);

    @FormUrlEncoded
    @POST("userChecklists")
    Call<ApiResponse> postChecklistByUserId(@Header("Authorization") String token,
                                            @Field("user_id") String userId,
                                            @Field("date") String date,
                                            @Field("checklists") String checklists);

    @GET("userChecklists")
    Call<ApiResponse<List<Checklist>>> getListChecklistUser(@Header("Authorization") String token,
                                                            @Query("user_id") String user_id,
                                                            @Query("date") String date);

    @GET("stocks")
    Call<ApiResponse<List<Stock>>> getDetailStockBarcode(@Header("Authorization") String token,
                                                         @Query("article_code") String barcode);

    @GET("discounts")
    Call<ApiResponse<List<Discount>>> getDiscount(@Header("Authorization") String token);

    @POST("transactions")
    Call<ApiResponse> postSalesReport(@Header("Authorization") String token,
                                      @Body SalesReport salesReport);

    @GET("transactions")
    Call<ApiResponse<List<SalesReportResponse>>> getSalesReport(@Header("Authorization") String token,
                                                                @Query("sales_id") String userId,
                                                                @Query("date") String date);

    @GET("competitorTransactions")
    Call<ApiResponse<List<KompetitorResponse>>> getPenjualanKompetitor(@Header("Authorization") String token,
                                                                       @Query("m_place_id") String placeId,
                                                                       @Query("from_date") String date);

    @POST("competitorTransactions")
    Call<ApiResponse> postPenjualanKompetitor(@Header("Authorization") String token,
                                              @Body Kompetitor data);

    @GET("items")
    Call<ApiResponse<List<Item>>> getListItems(@Header("Authorization") String token);

    @GET("categories")
    Call<ApiResponse<List<Category>>> getListCetegories(@Header("Authorization") String token);

    @GET("stocks")
    Call<ApiResponse<List<Stock>>> getListStockbyToko(@Header("Authorization") String token,
                                                      @Query("m_place_id") String idToko);

    @POST("stocks")
    Call<ApiResponse> postStockOpname(@Header("Authorization") String token,
                                      @Body StockOpnameModel data);

//    @GET("group")
//    Call<ApiResponse<List<Treatment>>> getAllInstitution(@Query("type") String type);
//
//    @POST("userDevice")
//    Call<ApiResponse> postUserDevice(@Body RequestBody userDeviceRequest);
//
//    @DELETE("userDevice/{device_id}")
//    Call<ApiResponse> removeUserDevice(@Path("device_id") String device_id);
//
//    @POST("profile")
//    Call<ApiResponse> updateProfile(@Body RequestBody profile);
//
//    @GET("profile")
//    Call<ApiResponse<User>> myProfile();
//
//    @POST("serviceTransaction")
//    Call<ApiResponse> createBiomedicalAppointmentOutreach(@Body RequestBody appointment);
//
//    @POST("topic")
//    Call<ApiResponse> createNewTopic(@Body RequestBody newTopicRequest);
//
//    @GET("topic")
//    Call<ApiResponse<List<Topic>>> getAllTopic();
//
//    @GET("topic/{topic_id}")
//    Call<ApiResponse<Topic>> getTopicDetail(@Path("topic_id") String topicId);
//
//    @GET("serviceTransaction/myAppointment")
//    Call<ApiResponse<List<GeneralDataResponse>>> getMyAppointmentList(@Query("user_id") String userId);
//
//    @POST("message")
//    Call<ApiResponse> sendMessage(@Body Datum chat);
//
//    @GET("generateToken")
//    Call<ApiResponse<Token>> generateToken(@Query("user_id") String userId);
//
//    @GET("serviceTransaction/reportStatus")
//    Call<ApiResponse<List<GeneralDataResponse>>> getMyReport(@Query("user_id") String userId);
//
//    @POST("forgot")
//    @FormUrlEncoded
//    Call<ApiResponse> postForgotPassword(@Field("email") String email);
//
//    @POST("recover")
//    @FormUrlEncoded
//    Call<ApiResponse> postRecoverPassword(@Field("email") String email, @Field("number") String number, @Field("password") String password);
//
//    /***************** News API *********************/
//    @GET("news")
//    Call<ApiResponse<List<News>>> getNews(@Query("status") int status);
//
//    @GET("news")
//    Call<ApiResponse<List<News>>> getNewsWithCategory(@Query("news_category_id") String newsCategoryId, @Query("status") int status);
//    @GET("newsCategories")
//    Call<ApiResponse<List<CategoryModel>>> getNewsCategories();
//
//    @GET("news/{news_id}")
//    Call<ApiResponse<News>> getNewsDetail(@Path("news_id") String id);
//
//    /***************** Article API *********************/
//    @GET("article")
//    Call<ApiResponse<List<Article>>> getArticle(@Query("status") int status);
//
//    @GET("article")
//    Call<ApiResponse<List<Article>>> getArticleWithCategory(@Query("article_category_id") String articleCategoryId, @Query("status") int status);
//
//    @GET("article/{article_id}")
//    Call<ApiResponse<Article>> getArticleDetail(@Path("article_id") String id);
//
//    @GET("articleCategories")
//    Call<ApiResponse<List<CategoryModel>>> getArticleCategories();
//
//    /***************** Event API *********************/
//    @GET("event")
//    Call<ApiResponse<List<Event>>> getEvent(@Query("status") int status);
//
//    @GET("event")
//    Call<ApiResponse<List<Event>>> getEventWithCategory(@Query("event_category_id") String eventCategoryId, @Query("status") int status);
//
//    @GET("event/{event_id}")
//    Call<ApiResponse<Event>> getEventDetail(@Path("event_id") String id);
//
//    @GET("eventCategories")
//    Call<ApiResponse<List<CategoryModel>>> getEventCategories();
//
//    /***************** List API *********************/
//    @GET("userList")
//    Call<ApiResponse<List<ListReminder>>> getListReminder(@Query("user_id") String userId, @Query("type") String type);
//    @GET("userList")
//    Call<ApiResponse<List<ListSaved>>> getListSaved(@Query("user_id") String userId, @Query("type") String type);
//
//    /***************** List Appointment History API *********************/
//
//    @GET("serviceTransaction/myHistory")
//    Call<ApiResponse<List<HistoryListResponse>>> getHistoryAppointment(@Query("user_id") String userId);
//
//    @GET("serviceTransaction/myHistory")
//    Call<ApiResponse<List<HistoryList>>> getHistoryAppointmentProvider(@Query("user_id") String userId);
//
//    @POST("rating")
//    Call<ApiResponse> createRating(@Body RequestBody rating);
//
//    @FormUrlEncoded
//    @POST("userList")
//    Call<ApiResponse> postCreateUserList(@Field("user_id") String userId, @Field("type") String type, @Field("type_id") String typeId, @Field("datetime") String datetime);
//
//    @DELETE("userList/{id}")
//    Call<ApiResponse> removeBookmark(@Path("id") String id);
//
//    @GET("messageHistory")
//    Call<ApiResponse<List<Datum>>> chatHistory(@Query("channel") String channel);
}
