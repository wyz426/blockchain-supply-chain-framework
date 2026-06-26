import axios from 'axios';
const url_head = 'http://localhost:8080/blockchain/';

export const getAllBlockchains = () => {
  return axios.get(`${url_head}/`)
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const getBlockchainByModuleId = (moduleId) => {
  return axios.get(`${url_head}/moduleId/${moduleId}`)
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const getBlockchainById = (blockchainId) => {
  console.log(blockchainId);
  return axios.get(`${url_head}/${blockchainId}`)
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    });
}

export const AddCorporationToBlockChain = (blockchainId, corporationId) => {
  console.log("adding corporation to blockchain", blockchainId, corporationId);
  return axios.post(`${url_head}/corporationId/${corporationId}`, { params: { blockchainId: blockchainId }})
    .then(response => {
      console.log(response.data);
      return response.data.data;
    })
    .catch(error => {
      console.log(error);
      return error;
    })
}
