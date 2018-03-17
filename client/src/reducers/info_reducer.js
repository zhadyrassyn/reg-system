import {
  FETCH_CITIES_SUCCESS,
  FETCH_CITIES_FAILURE
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
    default:
      return state
  }
}