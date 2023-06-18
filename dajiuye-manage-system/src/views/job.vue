<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <!-- <el-link href="/#/company" target="_blank">默认链接</el-link>
        <router-link to="/company">
          sada
        </router-link> -->
        <el-select placeholder="求职类型" v-model="query.jobType" clearable class="handle-select mr10" @change="selectChange">
          <el-option key="2" label="校招" value=2></el-option>
          <el-option key="3" label="实习" value=1></el-option>
          <el-option key="4" label="社招" value=3></el-option>
        </el-select>
        <el-cascader :options="jobCategories" :props="props2" filterable clearable :show-all-levels="false"
          placeholder="全部职位" style="width:110px;margin-right: 10px;" collapse-tags="true" @change="jobChange" />
        <el-cascader :options="areas" :props="props1" filterable clearable :show-all-levels="false" placeholder="全部城市"
          style="width:110px;margin-right: 10px;" collapse-tags="true" @change="cityChange" />
        <el-select v-model="query.jobSalary" placeholder="薪资要求" clearable class="handle-select mr10"
          @change="selectChange">
          <!-- <el-option key="2" label="不限" value=""></el-option> -->
          <el-option key="2" label="3k以下" value="3k以下"></el-option>
          <el-option key="3" label="3-5k" value="3-5k"></el-option>
          <el-option key="4" label="5-10k" value="5-10k"></el-option>
          <el-option key="5" label="10-15k" value="10-15k"></el-option>
          <el-option key="6" label="15-20k" value="15-20k"></el-option>
          <el-option key="7" label="30-50k" value="30-50k"></el-option>
          <el-option key="8" label="50k以上" value="50k以上"></el-option>
        </el-select>
        <el-select v-model="query.com_scale" placeholder="公司规模" clearable class="handle-select mr10"
          @change="selectChange">
          <!-- <el-option key="2" label="不限" value=""></el-option> -->
          <el-option key="2" label="0-20人" value="0-20人"></el-option>
          <el-option key="3" label="20-99人" value="20-99人"></el-option>
          <el-option key="4" label="100-499人" value="100-499人"></el-option>
          <el-option key="5" label="500-599人" value="500-599人"></el-option>
          <el-option key="6" label="1000-9999人" value="1000-9999"></el-option>
          <el-option key="7" label="10000人以上" value="10000人以上"></el-option>
        </el-select>
        <el-select v-model="query.jobEdu" placeholder="学历要求" clearable class="handle-select mr10" @change="selectChange">
          <!-- <el-option key="2" label="不限" value=""></el-option> -->
          <el-option key="2" label="初中及以下" value="初中及以下"></el-option>
          <el-option key="3" label="中专/中技" value="中专/中技"></el-option>
          <el-option key="4" label="高中" value="高中"></el-option>
          <el-option key="5" label="大专" value="大专"></el-option>
          <el-option key="6" label="本科" value="本科"></el-option>
          <el-option key="7" label="硕士" value="硕士"></el-option>
          <el-option key="8" label="博士" value="博士"></el-option>
        </el-select>
        <el-input v-model="query.query" style="width: 160px;" placeholder="请输入公司或职位名"
          class="handle-input mr10"></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" :icon="Plus">新增</el-button>
      </div>
      <!--  -->
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="jobTypeName" :show-overflow-tooltip="true" width="155" label="jobTypeName" align="center">
        </el-table-column>
        <el-table-column prop="jobName" :show-overflow-tooltip="true" label="jobName" width="155"
          align="center"></el-table-column>
        <el-table-column prop="jobPlace" :show-overflow-tooltip="true" label="jobPlace" width="155"
          align="center"></el-table-column>
        <el-table-column prop="jobSalary" :show-overflow-tooltip="true" label="jobSalary" width="155"
          align="center"></el-table-column>
        <el-table-column prop="company.comScale" :show-overflow-tooltip="true" width="155" label="comScale"
          align="center"></el-table-column>
        <el-table-column prop="jobEdu" :show-overflow-tooltip="true" label="jobEdu" width="155"
          align="center"></el-table-column>
        <!-- <el-table-column prop="jobComId" :show-overflow-tooltip="true" label="jobComId" width="155"
          align="center"></el-table-column> -->
        <el-table-column prop="company.comMinName" :show-overflow-tooltip="true" label="comMinName" width="155"
          align="center">
          <template #default="scope">
            <router-link :to="{ path: '/company', query: { name: scope.row.company.comFullName } }">
              {{ scope.row.company.comMinName }}
            </router-link>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220" align="center">
          <template #default="scope">
            <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)" v-permiss="15">
              编辑
            </el-button>
            <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row)" v-permiss="16">
              删除
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="jobRequirements" :show-overflow-tooltip="true" width="155"
          label="jobRequirements"></el-table-column>
        <el-table-column prop="jobResponsibilities" :show-overflow-tooltip="true" width="155"
          label="jobResponsibilities"></el-table-column>
        <el-table-column prop="jobId" :show-overflow-tooltip="true" label="jobId" width="155"
          align="center"></el-table-column>
        <el-table-column prop="jobAge" :show-overflow-tooltip="true" label="jobAge" width="155"
          align="center"></el-table-column>
        <el-table-column prop="jobDayPerWeek" :show-overflow-tooltip="true" label="jobDayPerWeek" width="155"
          align="center"></el-table-column>
        <el-table-column prop="jobImg" :show-overflow-tooltip="true" label="jobImg" width="155"
          align="center"></el-table-column>
        <el-table-column prop="jobSid" :show-overflow-tooltip="true" label="jobSid" width="155"></el-table-column>
        <el-table-column prop="jobUpdateTime" :show-overflow-tooltip="true" width="155"
          label="jobUpdateTime"></el-table-column>
        <el-table-column prop="jobReleaseTime" :show-overflow-tooltip="true" width="155"
          label="jobReleaseTime"></el-table-column>
        <el-table-column prop="email" :show-overflow-tooltip="true" width="155" label="email"></el-table-column>
        <el-table-column prop="jobType" :show-overflow-tooltip="true" width="155" label="jobType"></el-table-column>
        <el-table-column prop="jobAuthorId" :show-overflow-tooltip="true" width="155"
          label="jobAuthorId"></el-table-column>
        <el-table-column prop="jobStat" :show-overflow-tooltip="true" width="155" label="jobStat"></el-table-column>
        <el-table-column prop="jobViewCnt" :show-overflow-tooltip="true" width="155" label="jobViewCnt"></el-table-column>
        <el-table-column prop="jobTypeName" :show-overflow-tooltip="true" width="155"
          label="jobTypeName"></el-table-column>
        <el-table-column prop="jobPriority" :show-overflow-tooltip="true" width="155"
          label="jobPriority"></el-table-column>
        <el-table-column prop="jobIndustry" :show-overflow-tooltip="true" width="155"
          label="jobIndustry"></el-table-column>
        <el-table-column prop="jobDeadLine" :show-overflow-tooltip="true" width="155"
          label="jobDeadLine"></el-table-column>
        <el-table-column prop="jobWelfare" :show-overflow-tooltip="true" width="155" label="jobWelfare"></el-table-column>
        <el-table-column label="账户余额">
          <template #default="scope">￥{{ scope.row.money }}</template>
        </el-table-column>
        <el-table-column label="头像(查看大图)" align="center">
          <template #default="scope">
            <el-image class="table-td-thumb" :src="scope.row.jobImg" :z-index="10" :preview-src-list="[scope.row.jobImg]"
              preview-teleported>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.state === '成功' ? 'success' : scope.row.state === '失败' ? 'danger' : ''">
              {{ scope.row.state }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="注册时间"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
          :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="jobId" label-width="120px">
          <el-input disabled v-model="form.jobId"></el-input>
        </el-form-item>
        <el-form-item label="jobSalary" label-width="120px">
          <el-input v-model="form.jobSalary"></el-input>
        </el-form-item>
        <el-form-item label="jobName" label-width="120px">
          <el-input v-model="form.jobName"></el-input>
        </el-form-item>
        <el-form-item label="jobPlace" label-width="120px">
          <el-input v-model="form.jobPlace"></el-input>
        </el-form-item>
        <el-form-item label="jobEdu" label-width="120px">
          <el-input v-model="form.jobEdu"></el-input>
        </el-form-item>
        <el-form-item label="jobAge" label-width="120px">
          <el-input v-model="form.jobAge"></el-input>
        </el-form-item>
        <el-form-item label="jobDayPerWeek" label-width="120px">
          <el-input v-model="form.jobDayPerWeek"></el-input>
        </el-form-item>
        <el-form-item label="jobImg" label-width="120px">
          <el-input v-model="form.jobImg"></el-input>
        </el-form-item>
        <el-form-item label="jobComId" label-width="120px">
          <el-input v-model="form.jobComId"></el-input>
        </el-form-item>
        <el-form-item label="jobSid" label-width="120px">
          <el-input v-model="form.jobSid"></el-input>
        </el-form-item>
        <el-form-item label="jobUpdateTime" label-width="120px">
          <el-input v-model="form.jobUpdateTime"></el-input>
        </el-form-item>
        <el-form-item label="jobReleaseTime" label-width="120px">
          <el-input v-model="form.jobReleaseTime"></el-input>
        </el-form-item>
        <el-form-item label="email" label-width="120px">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="jobType" label-width="120px">
          <el-input v-model="form.jobType"></el-input>
        </el-form-item>
        <el-form-item label="jobAuthorId" label-width="120px">
          <el-input v-model="form.jobAuthorId"></el-input>
        </el-form-item>
        <el-form-item label="jobDetails" label-width="120px">
          <el-input v-model="form.jobDetails"></el-input>
        </el-form-item>
        <el-form-item label="jobStat" label-width="120px">
          <el-input v-model="form.jobStat"></el-input>
        </el-form-item>
        <el-form-item label="jobViewCnt" label-width="120px">
          <el-input v-model="form.jobViewCnt"></el-input>
        </el-form-item>
        <el-form-item label="jobPriority" label-width="120px">
          <el-input v-model="form.jobPriority"></el-input>
        </el-form-item>
        <el-form-item label="jobIndustry" label-width="120px">
          <el-input v-model="form.jobIndustry"></el-input>
        </el-form-item>
        <el-form-item label="jobTimeSpan" label-width="120px">
          <el-input v-model="form.jobTimeSpan"></el-input>
        </el-form-item>
        <el-form-item label="jobDeadLine" label-width="120px">
          <el-input v-model="form.jobDeadLine"></el-input>
        </el-form-item>
        <el-form-item label="jobResponsibilities" label-width="120px">
          <el-input v-model="form.jobResponsibilities"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveEdit">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { CascaderValue, ElMessage, ElMessageBox, rowProps } from 'element-plus';
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import { fetchData, jobs, updateJob, deleteJob,placedata, jobcatdataManage } from '../api/index';
import { Row } from 'element-plus/es/components/table-v2/src/components';

const isEdit = ref(false)

const mouseEnter = (row, column, cell, event) => {
  console.log("sad")
  console.log(row)
  isEdit.value = true
};

const query = reactive<QueryJob>({
  pageSize: 5,
  pageNum: 1,
  query: '',
  // cid: '',
  // jobType: '',
  // jobAge: '',
  // jobSalary: '',
  // city: '',
  // com_scale:''
});
const deleteParam=reactive({
  jobId:''
})
const tableData = ref<Job[]>([]);
const pageTotal = ref(0);
// 职位列表
const getJobs = () => {
  jobs(query).then(res => {
    console.log(res)
    console.log(query)
    tableData.value = res.data.list.list;
    pageTotal.value = res.data.list.total;
  });
};
getJobs();
// 地区分类列表
const areas: Cascade[] = reactive([])
const getAreas = () => {
  placedata().then(res => {
    // console.log(res)
    const r = handleAreaData(res.data.list)
    areas.push(...r)
  });
};
getAreas();
// 职位分类列表
const jobCategories: Cascade[] = reactive([])
const getJobCategories = () => {
  jobcatdataManage().then(res => {
    // console.log(res)
    const r = handleAreaData(res.data.list)
    jobCategories.push(...r)
  });
};
getJobCategories();
// 查询职位
const handleSearch = () => {
  // query.pageIndex = 1;
  getJobs();
};
// 分页导航
const handlePageChange = (val: number) => {
  query.pageNum = val;
  getJobs();
};
// 删除操作
const handleDelete = (row: Job) => {
  // console.log(row)
  deleteParam.jobId=row.jobId
  // 二次确认删除
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning'
  })
    .then(() => {
      deleteJob(deleteParam).then(res=>{
        console.log(res)
        if(res.data.data){
          ElMessage.success('删除成功');
          getJobs()
        }else{
          ElMessage.error('删除失败')
        }
      }).catch((e) => { 
        console.log(e)
        ElMessage.error('删除失败')
      });
    })
    
};
// 表格编辑时弹窗和保存
const editVisible = ref(false);
// 更新职位
const form = reactive<Job>({
  jobId: '',
  jobSalary: '',
  jobName: '',
  jobPlace: '',
  jobEdu: '',
  jobAge: '',
  jobDayPerWeek: '',
  jobImg: '',
  jobComId: '',
  jobSid: '',
  jobUpdateTime: new Date,
  jobReleaseTime: new Date,
  email: '',
  jobType: -1,
  jobAuthorId: '',
  jobRequirements: '',
  jobStat: '',
  jobViewCnt: '',
  jobPriority: '',
  jobIndustry: '',
  jobTimeSpan: '',
  jobDeadLine: '',
  jobResponsibilities: ''
});
// 更新职位
const update = () => {
  // 二次确认删除
  ElMessageBox.confirm('确定更新职位吗？', '提示', {
    type: 'warning'
  })
    .then(() => {
      updateJob(form).then(res => {
        console.log(res)
      })
      ElMessage.success('更新成功');
      // tableData.value.splice(index, 1);
    })
    .catch(() => { });
}
let idx: number = -1;
const handleEdit = (index: number, row: any) => {
  idx = index;
  console.log(row)
  form.jobId = row.jobId,
    form.jobSalary = row.jobSalary,
    form.jobName = row.jobName,
    form.jobPlace = row.jobPlace,
    form.jobEdu = row.jobEdu,
    form.jobAge = row.jobAge,
    form.jobDayPerWeek = row.jobDayPerWeek,
    form.jobImg = row.jobImg,
    form.jobComId = row.jobComId,
    form.jobSid = row.jobSid,
    form.jobUpdateTime = row.jobUpdateTime,
    form.jobReleaseTime = row.jobReleaseTime,
    form.email = row.email,
    form.jobType = row.jobType,
    form.jobAuthorId = row.jobAuthorId,
    form.jobRequirements = row.jobRequirements,
    form.jobStat = row.jobStat,
    form.jobViewCnt = row.jobViewCnt,
    form.jobPriority = row.jobPriority,
    form.jobIndustry = row.jobIndustry,
    form.jobTimeSpan = row.jobTimeSpan,
    form.jobDeadLine = row.jobDeadLine,
    form.jobResponsibilities = row.jobResponsibilities,
    editVisible.value = true;
};
const saveEdit = () => {
  editVisible.value = false;
  update();
  getJobs();
  ElMessage.success(`修改第 ${idx + 1} 行成功`);
  // tableData.value[idx].name = form.name;
  // tableData.value[idx].address = form.address;
};
// 职位级联选择器属性
const props1 = {
  // multiple: true, // 多选
  // checkStrictly: true,// 
  expandTrigger: 'hover' as const, // 悬浮展开二级菜单
}
// 地区级联选择器属性
const props2 = {
  expandTrigger: 'hover' as const, // 悬浮展开二级菜单
}
// 递归组装城市
const handleAreaData = (data: Cascade[]) => {
  const options: Cascade[] = reactive([])
  if (!(data && data.length)) {
    return options;
  }
  //定义变量
  data.forEach(item => {
    options.push({
      value: item.name,
      label: item.name,
      children: handleAreaData(item.children)
    });
  });
  return options;
}
// 职位类型切换
const jobChange = (value: CascaderValue) => {
  if (null == value) {
    query.query = ''
  } else {
    query.query = value[2]
  }
  getJobs();
  // console.log(query)
}
// 城市切换
const cityChange = (value: CascaderValue) => {
  console.log(value)
  query.city = ''
  if (null == value) {
    query.query = ''
  } else {
    query.city = value[1]
  }
  getJobs();
}
// el-select切换
const selectChange = () => {
  getJobs();
}
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #F56C6C;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
