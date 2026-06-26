import axios from 'axios';
import { initDefaultFields } from "@/api/customField";
import { error } from "autoprefixer/lib/utils";
const url_head = 'http://localhost:8080';

export const getSystemList = () => {
  return axios.get(`${url_head}/system/`)
    .then(response => {
      console.log(response.data.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const getSystemById = (id) => {
  console.log("System id: ", id);
  return axios.get(`${url_head}/system/id/${id}`)
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const createSystem = (system) => {
  console.log(system);
  return axios.post(`${url_head}/system/`, system)
    .then(response => {
      console.log(response.data);
      return response.data;
    });
}

export const createModule = (module, system_id) => {
  console.log(module);
  return axios.post(`${url_head}/module/`, module, { params: { systemId: system_id }})
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
}

export const createModuleList = async(selectedOptions, system_id) => {
  const selectedList = Object.values(selectedOptions)
  console.log("system_id:", system_id)
  console.log(selectedList)
  for (let i = 0; i < selectedList.length; i++) {
    if (selectedList[i] === null) {
      selectedList[i] = false
    }
    if (selectedList[i] === true) {
      console.log("创建模块", i)
      const module = { type: i + 1 }
      const res = await createModule(module, system_id)
      await initDefaultFields(res.id, res.code)
    }
  }
}

export const parseSystemStatus = (status) => {
  switch (status) {
    case 1:
      return "正常运行中";
    default:
      return "未知状态";
  }
}

export const getModuleList = (system_id) => {
  return axios.get(`${url_head}/module/system/${system_id}`)
    .then(response => {
      console.log(response.data.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    })
}

export const getCorporationsByModuleId = (module_id) => {
  console.log("module_id:", module_id);
  return axios.get(`${url_head}/corporation/moduleId/${module_id}`)
    .then(res => {
      console.log(res.data);
      return res.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const getCorporationBySystemId = (system_id) => {
  console.log("system_id: ", system_id);
  return axios.get(`${url_head}/corporation/systemId/${system_id}`)
    .then(res => {
      console.log(res.data.data);
      return res.data.data;
    })
    .catch(err => {
      console.log(err)
      return err;
    })
}
