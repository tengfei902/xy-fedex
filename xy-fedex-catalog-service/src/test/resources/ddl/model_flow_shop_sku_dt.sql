create table flow_shop_sku_dt
    comment '商品流量数据'
    tblproperties(
        'model.dsn'='testdsncode',
        'model.column_comment.expose_pv'='曝光pv',
        'model.column_comment.expose_uv'='曝光uv',
        'model.column_comment.click_uv'='点击uv',
        'model.show_name'='商品流量数据'
)
as
select
    count(if(user_action='expose',user_id,null)) as expose_pv,
    count(distinct if(user_action='expose',user_id,null)) as expose_uv,
    count(if(user_action='click',user_id,null)) as click_pv,
    count(distinct if(user_action='click',user_id,null)) as click_uv,
    count(if(user_action='view',user_id,null)) as view_pv,
    count(distinct if(user_action='view',user_id,null)) as view_uv,
    count(if(user_action='buy',user_id,null)) as buy_pv,
    count(distinct if(user_action='buy',user_id,null)) as buy_uv,
    count(distinct if(user_action='buy',user_id,null))/count(distinct if(user_action='view',user_id,null)) as visit_buy_rate,
    t1.shop_id as shop_id,
    t2.shop_name as shop_name,
    t2.shop_full_name as shop_full_name,
    t2.tenant_id as tenant_id,
    t2.tenant_name as tenant_name,
    t2.brand_id as brand_id,
    t2.brand_name as brand_name,
    t2.district_code as district_code,
    t2.business_district_id as business_district_id,
    t2.latitude as latitude,
    t2.longitude as longitude,
    t3.district_name as district_name,
    t3.city_code as city_code,
    t3.city_name as city_name,
    t3.province_code as province_code,
    t3.province_name as province_name,
    t3.country_code as country_code,
    t3.country_name as country_name,
    t4.day_of_month as day_of_month,
    t4.day_of_week as day_of_week,
    t4.day_of_year as day_of_year,
    t4.week_of_month as week_of_month,
    t4.week_of_year as week_of_year,
    t4.month_of_year as month_of_year,
    t4.year as year,
    t4.holiday as holiday,
    t5.sku_name as sku_name,
    t5.spu_id as spu_id,
    t5.spu_name as spu_name,
    t5.catagory_id as catagory5_id,
    t5.catagory_name as catagory5_name,
    t5.weight as weight,
    t5.volume as volume,
    t5.specifications as specifications,
    t5.manufacturer as manufacturer,
    t6.catagory1_id as catagory1_id,
    t6.catagory1_name as catagory1_name,
    t6.catagory2_id as catagory2_id,
    t6.catagory2_name as catagory2_name,
    t6.catagory3_id as catagory3_id,
    t6.catagory3_name as catagory3_name,
    t6.catagory4_id as catagory4_id,
    t6.catagory4_name as catagory4_name,
    t1.dt as dt
from dwd_flow_sku_d_ss t1
    left join dim_shop_ss t2 on t1.shop_id = t2.shop_id and t1.dt = t2.dt
    left join dim_city_ss t3 on t2.district_code = t3.district_code and t2.dt = t3.dt
    left join dim_dt_ss t4 on t1.dt = t4.dt
    left join dim_sku_ss t5 on t1.sku_id = t5.sku_id and t1.dt = t5.dt
    left join dim_catagory_ss t6 on t5.dt = t6.dt and t5.catagory_id = t6.catagory5_id
group by
    t1.shop_id,
    t2.shop_name,
    t2.shop_full_name,
    t2.tenant_id,
    t2.tenant_name,
    t2.brand_id,
    t2.brand_name,
    t2.district_code,
    t2.business_district_id,
    t2.latitude,
    t2.longitude,
    t3.district_name,
    t3.city_code,
    t3.city_name,
    t3.province_code,
    t3.province_name,
    t3.country_code,
    t3.country_name,
    t4.day_of_month,
    t4.day_of_week,
    t4.day_of_year,
    t4.week_of_month,
    t4.week_of_year,
    t4.month_of_year,
    t4.year,
    t4.holiday,
    t5.sku_name,
    t5.spu_id,
    t5.spu_name,
    t5.catagory_id,
    t5.catagory_name,
    t5.weight,
    t5.volume,
    t5.specifications,
    t5.manufacturer,
    t6.catagory1_id,
    t6.catagory1_name,
    t6.catagory2_id,
    t6.catagory2_name,
    t6.catagory3_id,
    t6.catagory3_name,
    t6.catagory4_id,
    t6.catagory4_name,
    t1.dt