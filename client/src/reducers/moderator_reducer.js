import _ from "lodash"
import {
  FETCH_STUDENTS_FAILURE,
  FETCH_STUDENTS_SUCCESS,
  FETCH_STUDENT_FULL_INFO_FAILURE,
  FETCH_STUDENT_FULL_INFO_SUCCESS
} from "../actions/types"

const initialState = {
  students: {},
  selectedStudent: {}
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
    case FETCH_STUDENT_FULL_INFO_SUCCESS:
      return {
        ...state,
        selectedStudent: action.data
      }
    case FETCH_STUDENT_FULL_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    default:
      return state
  }
}