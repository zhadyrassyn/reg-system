import { CHANGE_LANG } from "../actions/types"

export default (state = 'ru', action) => {
  switch (action.type) {
    case CHANGE_LANG:
      return action.lang
    default:
      return state
  }
}