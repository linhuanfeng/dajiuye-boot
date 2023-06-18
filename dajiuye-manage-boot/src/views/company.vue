<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-select placeholder="求职类型" v-model="query.jobType" clearable class="handle-select mr10" @change="selectChange">
          <el-option key="2" label="校招" value=2></el-option>
          <el-option key="3" label="实习" value=1></el-option>
          <el-option key="4" label="社招" value=3></el-option>
        </el-select>
        <el-cascader :options="jobCategories" :props="props2" filterable clearable :show-all-levels="false"
          placeholder="全部公司" style="width:110px;margin-right: 10px;" collapse-tags="true" @change="jobChange" />
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
        <el-input v-model="query.query" style="width: 160px;" placeholder="请输入公司或公司名"
          class="handle-input mr10"></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" :icon="Plus">新增</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="comId" :show-overflow-tooltip="true" width="155" label="comId"
          align="center"></el-table-column>
        <el-table-column prop="comFullName" :show-overflow-tooltip="true" label="comFullName" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comMinName" :show-overflow-tooltip="true" label="comMinName" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comScale" :show-overflow-tooltip="true" label="comScale" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comClass" :show-overflow-tooltip="true" label="comClass" width="155"
          align="center"></el-table-column>
        <el-table-column label="操作" width="220" align="center">
          <template #default="scope">
            <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)" v-permiss="15">
              编辑
            </el-button>
            <el-button text :icon="Delete" class="red" @click="handleDelete(scope.$index)" v-permiss="16">
              删除
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="公司Logo(查看大图)" align="center">
          <template #default="scope">
            <el-image class="table-td-thumb" :src="scope.row.comLogo" :z-index="10"
              :preview-src-list="[scope.row.comLogo]" preview-teleported>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="comAddr" :show-overflow-tooltip="true" label="comAddr" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comWebsite" :show-overflow-tooltip="true" label="comWebsite" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comIntro" :show-overflow-tooltip="true" label="comIntro" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comLicense" :show-overflow-tooltip="true" label="comLicense" width="155"></el-table-column>
        <el-table-column prop="incorporationDate" :show-overflow-tooltip="true" width="155"
          label="incorporationDate"></el-table-column>
        <el-table-column prop="comWelfare" :show-overflow-tooltip="true" width="155" label="comWelfare"></el-table-column>
        <el-table-column prop="comLinks" :show-overflow-tooltip="true" width="155" label="comLinks"></el-table-column>
        <el-table-column prop="jobWelfare" :show-overflow-tooltip="true" width="155" label="jobWelfare"></el-table-column>
        <el-table-column label="账户余额">
          <template #default="scope">￥{{ scope.row.money }}</template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.state === '成功' ? 'success' : scope.row.state === '失败' ? 'danger' : ''">
              {{ scope.row.state }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="comAuthCapital" :show-overflow-tooltip="true" label="comAuthCapital" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comMail" :show-overflow-tooltip="true" label="comMail" width="155"
          align="center"></el-table-column>
        <el-table-column prop="comIndustry" :show-overflow-tooltip="true" label="comIndustry" width="155"
          align="center"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
          :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="comId" label-width="120px">
          <el-input disabled v-model="form.comId"></el-input>
        </el-form-item>
        <el-form-item label="comFullName" label-width="120px">
          <el-input v-model="form.comFullName"></el-input>
        </el-form-item>
        <el-form-item label="comAuthCapital" label-width="120px">
          <el-input v-model="form.comAuthCapital"></el-input>
        </el-form-item>
        <el-form-item label="comIndustry" label-width="120px">
          <el-input v-model="form.comIndustry"></el-input>
        </el-form-item>
        <el-form-item label="comMail" label-width="120px">
          <el-input v-model="form.comMail"></el-input>
        </el-form-item>
        <el-form-item label="comAddr" label-width="120px">
          <el-input v-model="form.comAddr"></el-input>
        </el-form-item>
        <el-form-item label="comWebsite" label-width="120px">
          <el-input v-model="form.comWebsite"></el-input>
        </el-form-item>
        <el-form-item label="comIntro" label-width="120px">
          <el-input v-model="form.comIntro"></el-input>
        </el-form-item>
        <el-form-item label="comMinName" label-width="120px">
          <el-input v-model="form.comMinName"></el-input>
        </el-form-item>
        <el-form-item label="comScale" label-width="120px">
          <el-input v-model="form.comScale"></el-input>
        </el-form-item>
        <el-form-item label="comLicense" label-width="120px">
          <el-input v-model="form.comLicense"></el-input>
        </el-form-item>
        <el-form-item label="comLogo" label-width="120px">
          <el-input v-model="form.comLogo"></el-input>
        </el-form-item>
        <el-form-item label="comClass" label-width="120px">
          <el-input v-model="form.comClass"></el-input>
        </el-form-item>
        <el-form-item label="incorporationDate" label-width="120px">
          <el-input v-model="form.incorporationDate"></el-input>
        </el-form-item>
        <el-form-item label="comWelfare" label-width="120px">
          <el-input v-model="form.comWelfare"></el-input>
        </el-form-item>
        <el-form-item label="comLinks" label-width="120px">
          <el-input v-model="form.comLinks"></el-input>
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
import { ref, reactive, onMounted } from 'vue';
import { CascaderValue, ElMessage, ElMessageBox } from 'element-plus';
import { fetchData, companies, placedata, jobcatdataManage, updateCompany } from '../api/index';
import { useRoute } from 'vue-router';
const Route = useRoute()
onMounted(() => {
  console.log("获取到的参数", Route.query.name);
  query.query = Route.query.name
  getCompanies();
});

const query = reactive<QueryCompany>({
  pageSize: 5,
  pageNum: 1,
  query: ''
  // cid: '',
  // jobType: '',
  // jobAge: '',
  // jobSalary: '',
  // city: '',
  // com_scale:''
});
const tableData = ref<Job[]>([]);
const pageTotal = ref(0);
// 公司列表
const getCompanies = () => {
  companies(query).then(res => {
    console.log(res)
    console.log(query)
    tableData.value = res.data.list.list;
    pageTotal.value = res.data.list.total;
  });
};
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
// 公司分类列表
const jobCategories: Cascade[] = reactive([])
const getJobCategories = () => {
  jobcatdataManage().then(res => {
    // console.log(res)
    const r = handleAreaData(res.data.list)
    jobCategories.push(...r)
  });
};
getJobCategories();
// 查询公司
const handleSearch = () => {
  // query.pageIndex = 1;
  getCompanies();
};
// 分页导航
const handlePageChange = (val: number) => {
  query.pageNum = val;
  getCompanies();
};
// 删除操作
const handleDelete = (index: number) => {
  // 二次确认删除
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning'
  })
    .then(() => {
      ElMessage.success('删除成功');
      // tableData.value.splice(index, 1);
    })
    .catch(() => { });
};
// 表格编辑时弹窗和保存
const editVisible = ref(false);
// 更新公司
const form = reactive<Company>({
  comId: '',
  comFullName: '',
  comAuthCapital: '',
  comIndustry: '',
  comMail: '',
  comAddr: '',
  comWebsite: '',
  comIntro: '',
  comMinName: '',
  comScale: '',
  comLicense: '',
  comLogo: '',
  comClass: '',
  incorporationDate: '',
  comWelfare: '',
  comLinks: ''
});
const update = () => {
  updateCompany(form).then(res => {
    if (res.data.data) {
      ElMessage.success('更新成功');
      getCompanies()
    } else {
      ElMessage.error('更新失败')
    }
  }).catch((e) => {
    console.log(e)
    ElMessage.error('更新失败')
  });
}
let idx: number = -1;
const handleEdit = (index: number, row: any) => {
  idx = index;
  console.log(row)
  form.comId = row.comId,
    form.comFullName = row.comFullName,
    form.comAuthCapital = row.comAuthCapital,
    form.comIndustry = row.comIndustry,
    form.comMail = row.comMail,
    form.comAddr = row.comAddr,
    form.comWebsite = row.comWebsite,
    form.comIntro = row.comIntro,
    form.comMinName = row.comMinName,
    form.comScale = row.comScale,
    form.comLicense = row.comLicense,
    form.comLogo = row.comLogo,
    form.comClass = row.comClass,
    form.incorporationDate = row.incorporationDate,
    form.comWelfare = row.comWelfare,
    form.comLinks = row.comLinks,
    editVisible.value = true;
};
const saveEdit = () => {
  editVisible.value = false;
  update();
};
// 公司级联选择器属性
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
// 公司类型切换
const jobChange = (value: CascaderValue) => {
  if (null == value) {
    query.query = ''
  } else {
    query.query = value[2]
  }
  getCompanies();
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
  getCompanies();
}
// el-select切换
const selectChange = () => {
  getCompanies();
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
