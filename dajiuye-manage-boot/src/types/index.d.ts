// 公司查询参数
export interface QueryCompany {
  pageSize:number;
  pageNum:number;
  // query:string;
  // cid:string;
  // jobType:number;
  // jobAge:string;
  // jobSalary:string,
  // com_scale:string
}
// 公司
export interface Company{
  comId: string,
  comFullName:string,
  comAuthCapital: string,
  comIndustry: string,
  comMail: string,
  comAddr: string,
  comWebsite: string,
  comIntro: string,
  comMinName: string,
  comScale: string,
  comLicense: string,
  comLogo:string,
  comClass: string,
  incorporationDate: string,
  comWelfare: string,
  comLinks: string
}
interface TableItem {
  id: number;
  name: string;
  sno: string;
  class: string;
  age: string;
  sex: string;
}
// 职位查询参数
export interface QueryJob {
  pageSize:number;
  pageNum:number;
  query:string;
  cid:string;
  jobType:number;
  jobAge:string;
  jobSalary:string,
  com_scale:string
}
// 职位
export interface Job{
  jobId:string;
  jobSalary:string;
  jobName:string;
  jobPlace:string;
  jobEdu:string;
  jobAge:string;
  jobDayPerWeek:string;
  jobImg:string;
  jobComId:string;
  jobSid:string;
  jobUpdateTime:Date;
  jobReleaseTime:Date;
  email:string;
  jobType:number;
  jobTypeName:string
  jobAuthorId:string;
  jobRequirements:string;
  jobStat:string;
  jobViewCnt:string;
  jobPriority:string;
  jobIndustry:string;
  jobTimeSpan:string;
  jobDeadLine:string;
  jobResponsibilities:string;
}
// 导航
export interface CatItem{
  id:number;
  name:string;
  imageSrc:string;
  openType:string;
  navigatorUrl:string;
}
// 轮播
export interface Swiper{
  id:number;
  imageSrc:string;
  title:string;
  goodsId:number;
  navigatorUrl:string;
}
// 通用分页查询参数
export interface PageParam{
  pageSize:number;
  pageNum:number;
}
// 级联数据统一抽象
export interface Cascade {
  value: string | number | boolean
  label: string | number | boolean
  children:Area[]
}