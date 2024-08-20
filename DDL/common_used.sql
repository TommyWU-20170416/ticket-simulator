-- 用地點查詢餐廳
select restaurant_name, restaurant_image_url, restaurant_image_blob from public.restaurant
where station_id = '1';

-- 用地點查詢景點
select attraction_name, attraction_image_url, attraction_image_blob from public.attraction
left join region on attraction.region_id = region.region_id
where region.region_id = '1';
