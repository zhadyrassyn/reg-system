import {
  SIGN_UP_FAILURE,
  SIGN_UP_SUCCESS
} from "./types"

import axios from "axios"
import config from "../config"

export const singUp = ({email, password}) => (dispatch) => {
  const request = `${config.url}/auth/signup`

  axios.post(request, {email, password})
    .then(response => {
      dispatch({
        type: SIGN_UP_SUCCESS
      })
    })
    .catch(error => {
      dispatch({
        type: SIGN_UP_FAILURE,
        message: error.response && error.response.message
      })
    })
}