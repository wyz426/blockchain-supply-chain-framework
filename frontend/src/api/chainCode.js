import axios from 'axios';
const url = "http://localhost:8080/chaincode"

export const createChainCode = (chainCode, channelId) => {
  console.log(chainCode);
  console.log(channelId);
  if (channelId === null) { channelId = 0; }
  return axios.post(`${url}/`, chainCode, { params: { channelId: channelId }})
    .then(res => {
      console.log(res.data);
      return res.data.data;
    })
    .catch(err => console.log(err));
}
