import {
  SAVE_STUDENT_DOCUMENT_FAILURE,
  SAVE_STUDENT_DOCUMENT_SUCCESS
} from "../actions/types"

import {
  IN_WAITING,
  ACCEPTED,
  REJECTED
} from "../constants/index"

const initialState = {
  documentsStatus: {}
}

export default (state = initialState, action) => {
  switch (action.type) {
    case SAVE_STUDENT_DOCUMENT_SUCCESS:
      return {
        ...state,
        documentsStatus: {
          ...state,
          [action.documentType]: IN_WAITING
        }
      }
    case SAVE_STUDENT_DOCUMENT_FAILURE:
      return {
        ...state,
        error: action.error
      }
    default:
      return state
  }
}