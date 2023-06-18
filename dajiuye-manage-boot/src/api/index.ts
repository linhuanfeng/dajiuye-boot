import request from '../utils/request';

 const fetchData = () => {
    return request({
        url: './table.json',
        method: 'get'
    });
};
// 公司列表
const companies = (options:QueryCompany) => {
  return request({
      url: '/job/company/list',
      method: 'get',
      params:options
  });
};
// 更新公司
const updateCompany = (options:QueryCompany) => {
  return request({
      url: '/job/company/updateCompany',
      method: 'post',
      data:options
  });
};
// 职位列表
 const jobs = (options:QueryJob) => {
  return request({
      url: '/job/job/jobsManage',
      method: 'get',
      params:options
  });
};
// 更新职位
const updateJob = (options:Job) => {
  return request({
      url: '/job/job/updateJob',
      method: 'post',
      data:options
  });
};
// 删除职位
const deleteJob = (options:any) => {
  return request({
      url: '/job/job/delete',
      method: 'get',
      params:options
  });
};
// 保存职位
const saveJobBoot = (options:Job) => {
  return request({
      url: '/job/job/saveJobBoot',
      method: 'post',
      data:options
  });
};
// 地区分类列表
const placedata = () => {
  return request({
      url: '/swipper/area/placedataManage',
      method: 'get'
  });
};
// 职位分类列表
const jobcatdataManage = () => {
  return request({
      url: 'job/category/jobcatdataManage',
      method: 'get'
  });
};
// 导航列表 catitems
const catitems = (options:PageParam) => {
  return request({
      url: 'swipper/catItems/catitems',
      method: 'get',
      params:options
  });
};
// 更新导航列表 
const updateCatitems = (options:CatItem) => {
  return request({
      url: 'swipper/catItems/updateCatItems',
      method: 'post',
      data:options
  });
};
// 轮播图列表 
const swiperdata = (options:PageParam) => {
  return request({
      url: 'swipper/swipper/swiperdata',
      method: 'get',
      params:options
  });
};
// 更新轮播图 
const updateSwiper = (options:Swiper) => {
  return request({
      url: 'swipper/swipper/updateSwiper',
      method: 'post',
      data:options
  });
};
export {fetchData,updateJob,jobs,placedata,jobcatdataManage,companies,saveJobBoot,deleteJob,catitems,updateCatitems,swiperdata,updateSwiper,updateCompany}