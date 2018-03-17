import {
  FETCH_CITIES_SUCCESS,
  FETCH_CITIES_FAILURE,
  FETCH_SCHOOLS_FAILURE,
  FETCH_SCHOOLS_SUCCESS
} from "../actions/types"

const initialState = {
  cities: [],
  schools: []
}

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_CITIES_SUCCESS:
      return {
        ...state,
        cities: action.data
      }
    case FETCH_CITIES_FAILURE:
      return {
        ...state,
        error: action.message
      }
    case FETCH_SCHOOLS_SUCCESS:
      return {
        ...state,
        schools: action.data
      }
    case FETCH_SCHOOLS_FAILURE:
      return {
        ...state,
        error: action.message
      }
    default:
      return state
  }
}