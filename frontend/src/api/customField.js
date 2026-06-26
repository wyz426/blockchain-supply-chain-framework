import axios from "axios";
import { defaultFields } from "@/assets/data/defaultFields";
const url_head = "http://localhost:8080/customField"

// @PostMapping("/")
//     public CustomField createCustomField(@RequestBody CustomField customField, @RequestParam("moduleId") Integer moduleId) {
export const createCustomField = (customField, moduleId) => {
  console.log(customField);
  console.log(moduleId);
  if(!customField.source) { customField.source = "无" }
  return axios.post(`${url_head}/`, customField, { params: { moduleId: moduleId }})
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const initDefaultFields = async(moduleId, moduleCode) => {
  const defaultFieldsData = defaultFields[moduleCode]
  for (const field of defaultFieldsData) {
    await createCustomField(field, moduleId)
  }
}

// @GetMapping("/module")
//     public List<CustomField> getCustomFields(
//             @RequestParam("moduleId") Integer moduleId,
//             @RequestParam("objectCode.js") String objectCode.js
//     )
export const getCustomFields = (moduleId, objectCode) => {
  return axios.get(`${url_head}/module`, { params: { moduleId: moduleId, objectCode: objectCode }})
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const deleteField = (fieldId) => {
  return axios.delete(`${url_head}/del/${fieldId}`)
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    })
}

export const editField = (customField) => {
  return axios.post(`${url_head}/edit`, customField)
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    })
}

export const parseFieldType = (fieldType) => {
  switch (fieldType) {
    case "text":
      return "文本";
    case "num":
      return "整数";
    case "float":
      return "浮点数";
    case "date":
      return "日期";
    case "gps":
      return "GPS";
    case "pic":
      return "图片";
    case "file":
      return "文件";
  }
}
