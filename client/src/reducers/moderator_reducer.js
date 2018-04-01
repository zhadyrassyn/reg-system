import _ from "lodash"
import {
  FETCH_STUDENTS_FAILURE,
  FETCH_STUDENTS_SUCCESS
} from "../actions/types"

const initialState = {
  students: {}
}

export default (state = initialState, action) => {
  switch (action.type) {
    case FETCH_STUDENTS_SUCCESS:
      return {
        ...state,
        students: _.mapKeys(action.data, "id")
      }
    case FETCH_STUDENTS_FAILURE:
      return {
        ...state,
        error: action.error
      }
    default:
      return state
  }
}