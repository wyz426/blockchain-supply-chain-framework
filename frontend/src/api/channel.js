import axios from "axios"
const url = "http://localhost:8080/channel"

export const createChannel = (channel, moduleId) => {
  console.log(channel)
  console.log(moduleId)
  return axios.post(`${url}/`, channel, { params: { moduleId: moduleId }})
    .then(res => {
      console.log(res.data)
      return res.data.data;
    })
    .catch(err => {
      console.log(err)
      return err;
    });
}

export const getChannelByModuleId = (moduleId) => {
  return axios.get(`${url}/channel`, { params: { moduleId: moduleId }})
    .then(res => {
      console.log(res.data)
      return res.data.data;
    })
    .catch(err => {
      console.log(err)
      return err;
    });
}
