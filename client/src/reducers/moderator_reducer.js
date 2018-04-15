import _ from "lodash"
import {
  FETCH_STUDENTS_FAILURE,
  FETCH_STUDENTS_SUCCESS,
  FETCH_STUDENT_FULL_INFO_FAILURE,
  FETCH_STUDENT_FULL_INFO_SUCCESS,
  EDIT_STUDENT_GENERAL_INFO_FAILURE,
  EDIT_STUDENT_GENERAL_INFO_SUCCESS,
  SAVE_STUDENT_DOCUMENTS_COMMENT_FAILURE,
  SAVE_STUDENT_DOCUMENTS_COMMENT_SUCCESS,
  CHANGE_STUDENT_DOCUMENT_STATUS_SUCCESS,
  CHANGE_STUDENT_DOCUMENT_STATUS_FAILURE,
  FETCH_TOTAL_AMOUNT_OF_STUDENTS_FAILURE,
  FETCH_TOTAL_AMOUNT_OF_STUDENTS_SUCCESS
} from "../actions/types"

const initialState = {
  students: {},
  selectedStudent: {},
  total: 0
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
      action.data.documents = _.mapKeys(action.data.documents, "id")
      return {
        ...state,
        selectedStudent: action.data,
      }
    case FETCH_STUDENT_FULL_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case EDIT_STUDENT_GENERAL_INFO_SUCCESS:
      return {
        ...state,
        selectedStudent: {
          ...state.selectedStudent,
          generalInfoComment: action.data.comment,
          generalInfoStatus: action.data.status
        }
      }
    case EDIT_STUDENT_GENERAL_INFO_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case SAVE_STUDENT_DOCUMENTS_COMMENT_SUCCESS:
      return {
        ...state,
        selectedStudent: {
          ...state.selectedStudent,
          documentsComment: action.data.comment
        }
      }
    case SAVE_STUDENT_DOCUMENTS_COMMENT_FAILURE:
      return {
        ...state,
        error: action.error
      }

    case CHANGE_STUDENT_DOCUMENT_STATUS_SUCCESS:
      return {
        ...state,
        selectedStudent : {
          ...state.selectedStudent,
          documents: {
            ...state.selectedStudent.documents,
            [action.data.id] : action.data
          }
        }
      }
    case CHANGE_STUDENT_DOCUMENT_STATUS_FAILURE:
      return {
        ...state,
        error: action.error
      }
    case FETCH_TOTAL_AMOUNT_OF_STUDENTS_SUCCESS:
      return {
        ...state,
        total: action.data.total
      }
    case FETCH_TOTAL_AMOUNT_OF_STUDENTS_FAILURE:
      return {
        ...state,
        error: action.error
      }
    default:
      return state
  }
}