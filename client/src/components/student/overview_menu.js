import React, { Component } from "react"
import {message} from "../../locale/message"

import {
  FORM_GENERAL,
  FORM_DOCUMENTS,

  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_MEDICAL_INFO,
} from "../../constants"

const FORMS = [
  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_MEDICAL_INFO,
]

export default class OverviewMenu extends Component{

  constructor(props) {
    super(props)
  }

  onGeneralInfoBtnClick = () => {
    this.props.changeForm(FORM_GENERAL)
  }

  changeForm = (form) => {
    this.props.changeForm(form)
  }

  renderList = (activeForm, lang) => {
    return FORMS.map(form => {
      return (
        <li className="nav-item" key={form}>
          <button
            className={ "mr-2 nav-link btn " + (activeForm === form ? "active" : "btn-info") } onClick={this.changeForm.bind(this, form)}>
            {message[form][lang]}
          </button>
        </li>
      )
    })
  }

  render() {
    const {activeForm, lang} = this.props

    console.log('active form ', activeForm)
    return (
      <div className="container-fluid">
        <div className="my-3">
          <div className="nav justify-content-between">
            <div/>
            <ul className="nav nav-pills">
              {this.renderList(activeForm, lang)}
            </ul>
          </div>
        </div>
      </div>
    )
  }
}
