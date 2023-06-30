<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="药品名称" prop="drugName">
        <el-input
          v-model="queryParams.drugName"
          placeholder="请输入药品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产厂家" >
        <el-input
          v-model="queryParams['pillFactory.factoryName']"
          placeholder="请输入生产厂家"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="药品类型" prop="drugType">
        <el-select v-model="queryParams.drugType" placeholder="请选择药品类型" clearable>
          <el-option
            v-for="dict in dict.type.pill_drug_dt"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处方类型" prop="prescriptionType">
        <el-select v-model="queryParams.prescriptionType" placeholder="请选择处方类型" clearable>
          <el-option
            v-for="dict in dict.type.pill_drug_pt"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pill:drug:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pill:drug:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pill:drug:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pill:drug:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="drugList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="药品ID" align="center" prop="drugId"/>
      <el-table-column label="药品名称" align="center" prop="drugName"/>
      <el-table-column label="药品编码" align="center" prop="drugCode"/>
      <el-table-column label="厂家名称" align="center" prop="pillFactory.factoryName" min-width="200"/>
      <el-table-column label="厂家编码" align="center" prop="pillFactory.factoryCode"/>
      <!--      <el-table-column label="生产厂家" align="center" prop="factoryId" />-->
      <el-table-column label="药品类型" align="center" prop="drugType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pill_drug_dt" :value="scope.row.drugType"/>
        </template>
      </el-table-column>
      <el-table-column label="处方类型" align="center" prop="prescriptionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pill_drug_pt" :value="scope.row.prescriptionType"/>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unit"/>
      <el-table-column label="价格" align="center" prop="price"/>
      <el-table-column label="库存量" align="center" prop="num"/>
      <el-table-column label="预警值" align="center" prop="warnValue"/>
      <el-table-column label="换算量" align="center" prop="conversion"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pill:drug:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pill:drug:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改药品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="药品名称" prop="drugName">
          <el-input v-model="form.drugName" placeholder="请输入药品名称"/>
        </el-form-item>
        <el-form-item label="药品编码" prop="drugCode">
          <el-input v-model="form.drugCode" placeholder="请输入药品编码"/>
        </el-form-item>
        <el-form-item label="生产厂家" prop="factoryId">
          <el-select v-model="form.factoryId" placeholder="请选择生产厂家">
            <el-option
              v-for="item in factoryList"
              :key="item.factoryId"
              :label="item.factoryName"
              :value="item.factoryId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="药品类型" prop="drugType">
          <el-select v-model="form.drugType" placeholder="请选择药品类型">
            <el-option
              v-for="dict in dict.type.pill_drug_dt"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处方类型" prop="prescriptionType">
          <el-select v-model="form.prescriptionType" placeholder="请选择处方类型">
            <el-option
              v-for="dict in dict.type.pill_drug_pt"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位"/>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="库存量" prop="num">
          <el-input v-model="form.num" placeholder="请输入库存量"/>
        </el-form-item>
        <el-form-item label="预警值" prop="warnValue">
          <el-input v-model="form.warnValue" placeholder="请输入预警值"/>
        </el-form-item>
        <el-form-item label="换算量" prop="conversion">
          <el-input v-model="form.conversion" placeholder="请输入换算量"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listDrug, getDrug, delDrug, addDrug, updateDrug} from "@/api/pill/drug";
import {listFactory} from "@/api/pill/factory";

export default {
  name: "Drug",
  dicts: ['pill_drug_pt', 'pill_drug_dt', 'sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 药品信息表格数据
      drugList: [],
      //厂家数据
      factoryList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        drugName: null,
        // factoryId: null,
        'pillFactory.factoryName': null,
        drugType: null,
        prescriptionType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        drugName: [
          {required: true, message: "药品名称不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getFactoryList();
  },
  methods: {
    /** 查询药品信息列表 */
    getList() {
      this.loading = true;
      listDrug(this.queryParams).then(response => {
        this.drugList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getFactoryList() {
      listFactory().then(response => {
        this.factoryList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        drugId: null,
        drugName: null,
        drugCode: null,
        factoryId: null,
        drugType: null,
        prescriptionType: null,
        unit: null,
        price: null,
        num: null,
        warnValue: null,
        conversion: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams['pillFactory.factoryName'] = '';
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.drugId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加药品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const drugId = row.drugId || this.ids
      getDrug(drugId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改药品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.drugId != null) {
            updateDrug(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDrug(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const drugIds = row.drugId || this.ids;
      this.$modal.confirm('是否确认删除药品信息编号为"' + drugIds + '"的数据项？').then(function () {
        return delDrug(drugIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pill/drug/export', {
        ...this.queryParams
      }, `drug_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
