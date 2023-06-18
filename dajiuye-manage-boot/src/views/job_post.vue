<template>
  <div class="container">
    <div>
      <el-form ref="ruleFormRef" :model="form" :rules="rules" label-width="70px" :size="formSize">
        <el-form-item label="jobComId" label-width="120px" prop="jobComId">
          <el-input v-model="form.jobComId"></el-input>
        </el-form-item>
        <el-form-item label="jobSalary" label-width="120px" prop="jobSalary">
          <!-- <el-input v-model="form.jobSalary"></el-input> -->
          <el-select v-model="form.jobSalary" placeholder="薪资要求" clearable class="handle-select mr10">
            <!-- <el-option key="2" label="不限" value=""></el-option> -->
            <el-option key="2" label="3k以下" value="3k以下"></el-option>
            <el-option key="3" label="3-5k" value="3-5k"></el-option>
            <el-option key="4" label="5-10k" value="5-10k"></el-option>
            <el-option key="5" label="10-15k" value="10-15k"></el-option>
            <el-option key="6" label="15-20k" value="15-20k"></el-option>
            <el-option key="7" label="30-50k" value="30-50k"></el-option>
            <el-option key="8" label="50k以上" value="50k以上"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="jobName" label-width="120px" prop="jobName">
          <el-input v-model="form.jobName"></el-input>
        </el-form-item>
        <el-form-item label="jobPlace" label-width="120px" prop="jobPlace">
          <!-- <el-input v-model="form.jobPlace"></el-input> -->
          <el-cascader :options="areas" :props="props1" filterable clearable :show-all-levels="false" placeholder="全部城市"
            style="width:110px;margin-right: 10px;" collapse-tags="true" @change="cityChange" />
        </el-form-item>
        <el-form-item label="jobEdu" label-width="120px" prop="jobEdu">
          <el-select v-model="form.jobEdu" placeholder="学历要求" clearable class="handle-select mr10">
            <!-- <el-option key="2" label="不限" value=""></el-option> -->
            <el-option key="2" label="初中及以下" value="初中及以下"></el-option>
            <el-option key="3" label="中专/中技" value="中专/中技"></el-option>
            <el-option key="4" label="高中" value="高中"></el-option>
            <el-option key="5" label="大专" value="大专"></el-option>
            <el-option key="6" label="本科" value="本科"></el-option>
            <el-option key="7" label="硕士" value="硕士"></el-option>
            <el-option key="8" label="博士" value="博士"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="jobAge" label-width="120px" prop="jobAge">
          <el-input v-model="form.jobAge"></el-input>
        </el-form-item>
        <el-form-item label="jobDayPerWeek" label-width="120px" prop="jobDayPerWeek">
          <el-input v-model="form.jobDayPerWeek"></el-input>
        </el-form-item>
        <el-form-item label="jobImg" label-width="120px" prop="jobImg">
          <el-input v-model="form.jobImg"></el-input>
        </el-form-item>
        <el-form-item label="岗位分类" label-width="120px" prop="jobCategories">
          <el-cascader :options="jobCategories" :props="props2" filterable clearable :show-all-levels="false"
            placeholder="全部职位" style="width:110px;margin-right: 10px;" collapse-tags="true"
            @change="jobCategoriesChange" />
        </el-form-item>
        <el-form-item label="email" label-width="120px" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="jobType" label-width="120px" prop="jobType">
          <el-select placeholder="求职类型" v-model="form.jobType" clearable class="handle-select mr10">
            <el-option key="2" label="校招" value=2></el-option>
            <el-option key="3" label="实习" value=1></el-option>
            <el-option key="4" label="社招" value=3></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="jobAuthorId" label-width="120px" prop="jobAuthorId">
          <el-input v-model="form.jobAuthorId"></el-input>
        </el-form-item>
        <el-form-item label="jobStat" label-width="120px" prop="jobStat">
          <el-input v-model="form.jobStat"></el-input>
        </el-form-item>
        <el-form-item label="jobViewCnt" label-width="120px" prop="jobViewCnt">
          <el-input v-model="form.jobViewCnt"></el-input>
        </el-form-item>
        <el-form-item label="jobPriority" label-width="120px" prop="jobPriority">
          <el-input v-model="form.jobPriority"></el-input>
        </el-form-item>
        <el-form-item label="jobIndustry" label-width="120px" prop="jobIndustry">
          <el-input v-model="form.jobIndustry"></el-input>
        </el-form-item>
        <el-form-item label="jobTimeSpan" label-width="120px" prop="jobTimeSpan">
          <el-input v-model="form.jobTimeSpan"></el-input>
        </el-form-item>
        <el-form-item label="jobDeadLine" label-width="120px" prop="jobDeadLine">
          <el-input v-model="form.jobDeadLine"></el-input>
        </el-form-item>
        <el-form-item label="岗位职责" label-width="120px" prop="jobRequirements">
          <!-- <el-input v-model="form.jobRequirements"></el-input> -->
          <!-- <div class="mgb20" ref="editor"></div> -->
          <Editor style="width: 100%;" @update:editorValue="updateJobRequirements" ref="editorRef"
            v-model="form.jobRequirements" :editorValue="form.jobRequirements"></Editor>
        </el-form-item>
        <el-form-item label="岗位要求" label-width="120px" prop="jobResponsibilities">
          <Editor style="width: 100%;" @update:editorValue="updateJobResponsibilities" ref="editorRef"
            v-model="form.jobResponsibilities" :editorValue="form.jobResponsibilities"></Editor>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit(ruleFormRef)">Create</el-button>
          <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts" name="editor">
import { CascaderValue, ElMessage, ElMessageBox, rowProps, FormInstance, FormRules } from 'element-plus';
import Editor from '../components/Editor/Editor.vue'
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import { placedata, saveJobBoot, jobcatdataManage } from '../api/index';
import { useRouter } from 'vue-router';
const router = useRouter()
// 地区级联选择器属性
const props1 = {
  expandTrigger: 'hover' as const, // 悬浮展开二级菜单
  multiple: true // 多选
}
// 岗位分类级联选择器属性
const props2 = {
  expandTrigger: 'hover' as const, // 悬浮展开二级菜单
}
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
// 职位类型切换
const jobCategoriesChange = (value: CascaderValue) => {
  console.log("jobCategoriesChange")
  console.log(value)
  if (null == value) {
    form.jobSid = '',
      form.jobMid = ''
  } else {
    form.jobSid = value[1]
    form.jobMid = value[2]
  }
  // console.log(query)
}
// 查询职位
// 城市切换
const cityChange = (value: CascaderValue) => {
  form.jobPlace = ''
  if (null == value) {
    form.jobPlace = ''
  } else {
    value.forEach(element => {
      form.jobPlace += element[1] + '/'
    });
  }
}
// 地区分类列表
const areas: Cascade[] = reactive([])
const getAreas = () => {
  placedata().then(res => {
    // console.log(res)
    const r = handleAreaData(res.data.list)
    areas.push(...r)
  });
};
// 递归组装城市
const handleAreaData = (data: Cascade[]) => {
  const options: Cascade[] = reactive([])
  if (!(data && data.length)) {
    return options;
  }
  //定义变量
  data.forEach(item => {
    options.push({
      value: item.id,
      label: item.name,
      children: handleAreaData(item.children)
    });
  });
  return options;
}
getAreas();
// 更新岗位职责
const updateJobRequirements = (value: any) => {
  form.jobRequirements = value
}
// 更新岗位要求
const updateJobResponsibilities = (value: any) => {
  form.jobResponsibilities = value
}
// 表单校验
const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  jobSalary: [
    {
      type: 'string',
      required: true,
      message: 'Please pick a salary',
      trigger: 'change',
    },
  ],
  jobName: [
    { required: true, message: 'Please input Activity name', trigger: 'blur' },
    { min: 1, max: 15, message: 'Length should be 1 to 15', trigger: 'blur' },
  ],
  jobPlace: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'change',
    },
  ],
  jobType: [
    {
      required: true,
      message: 'Please select Activity count',
      trigger: 'change',
    },
  ],
  date1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  type: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one activity type',
      trigger: 'change',
    },
  ],
  jobComId: [
    {
      required: true,
      message: 'Please select activity resource',
      trigger: 'change',
    },
  ],
  desc: [
    { required: true, message: 'Please input activity form', trigger: 'blur' },
  ],
})
// 岗位发布
const onSubmit = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
      saveJobBoot(form).then(res => {
        console.log(res)
      })
      ElMessage.success('发布成功');
      router.push('/job')
    } else {
      console.log('error submit!', fields)
    }
  })
}
const resetForm = (formEl: FormInstance | undefined) => {
  console.log('reset')
  if (!formEl) return
  formEl.resetFields()
}


// const onSubmit = () => {
//     // 二次确认发布
//     ElMessageBox.confirm('确定发布职位吗？', '提示', {
//     type: 'warning'
//   })
//     .then(() => {
//       console.log(form)
//   saveJobBoot(form).then(res => {
//     console.log(res)
//   })
//       ElMessage.success('发布成功');
//       // tableData.value.splice(index, 1);
//     })
//     .catch(() => { });
// }
// 职位表单
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
  jobMid: '',
  jobUpdateTime: new Date,
  jobReleaseTime: new Date,
  email: '',
  jobType: '',
  jobAuthorId: 0,
  jobStat: '',
  jobViewCnt: '',
  jobPriority: '',
  jobIndustry: '',
  jobTimeSpan: '',
  jobDeadLine: '',
  jobRequirements: '',
  jobResponsibilities: ''
});
</script>

<style></style>
