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
  FETCH_TOTAL_AMOUNT_OF_STUDENTS_SUCCESS,
  SELECT_STUDENT,
  FETCH_PERSONAL_INFO_SUCCESS_MODERATOR,
  FETCH_PERSONAL_INFO_FAILURE_MODERATOR,
  FETCH_EDUCATION_INFO_SUCCESS_MODERATOR,
  FETCH_EDUCATION_INFO_FAILURE_MODERATOR,
  SAVE_EDUCATION_COMMENT_SUCCESS_MODERATOR,
  SAVE_EDUCATION_COMMENT_FAILURE_MODERATOR,
  FETCH_MEDICAL_INFO_SUCCESS_MODERATOR,
  FETCH_MEDICAL_INFO_FAILURE_MODERATOR,
  SAVE_MEDICAL_COMMENT_SUCCESS_MODERATOR,
  SAVE_MEDICAL_COMMENT_FAILURE_MODERATOR
} from "../actions/types"

const initialState = {
  students: {},
  selectedStudent: {},
  total: 0,
  currentStudentId: 0,
  personalInfo: {},
  educationInfo: {},
  medicalInfo: {}
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
        },
        personalInfo: {
          ...state.personalInfo,
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
        personalInfo: {
          ...state.personalInfo,
          comment: action.data.comment,
          status: action.data.status
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
    case SELECT_STUDENT:
      return {
        ...state,
        currentStudentId: action.id
      }
    case FETCH_PERSONAL_INFO_SUCCESS_MODERATOR:
      return {
        ...state,
        personalInfo: action.data
      }
    case FETCH_PERSONAL_INFO_FAILURE_MODERATOR:
      return {
        error: action.error
      }
    case FETCH_EDUCATION_INFO_SUCCESS_MODERATOR:
      return {
        ...state,
        educationInfo: action.data
      }
    case FETCH_EDUCATION_INFO_FAILURE_MODERATOR:
      return {
        error: action.error
      }
    case SAVE_EDUCATION_COMMENT_SUCCESS_MODERATOR:
      return {
        ...state,
        educationInfo: {
          ...state.educationInfo,
          comment: action.data.comment,
          status: action.data.status
        }
      }
    case SAVE_EDUCATION_COMMENT_FAILURE_MODERATOR:
      return {
        error: action.error
      }
    case FETCH_MEDICAL_INFO_SUCCESS_MODERATOR:
      return {
        ...state,
        medicalInfo: action.data
      }
    case FETCH_MEDICAL_INFO_FAILURE_MODERATOR:
      return {
        error: action.error
      }
    case SAVE_MEDICAL_COMMENT_SUCCESS_MODERATOR:
      return {
        ...state,
        medicalInfo: {
          ...state.medicalInfo,
          comment: action.data.comment,
          status: action.data.status
        }
      }
    case SAVE_MEDICAL_COMMENT_FAILURE_MODERATOR:
      return {
        error: action.error
      }
    default:
      return state
  }
}