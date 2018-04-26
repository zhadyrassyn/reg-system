import React, { Component } from "react"

import {
  FORM_GENERAL,
  FORM_DOCUMENTS,

  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_MEDICAL_INFO,
  FORM_CERTIFICATES_INFO
} from "../../constants"

const FORMS = [
  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_MEDICAL_INFO,
  FORM_CERTIFICATES_INFO
]

export default class OverviewMenu extends Component{

  constructor(props) {
    super(props)
  }

  onGeneralInfoBtnClick = () => {
    this.props.changeForm(FORM_GENERAL)
  }

  onDocumentsBtnClick = () => {
    this.props.changeForm(FORM_DOCUMENTS)
  }

  renderList = (activeForm) => {
    return FORMS.map(form => {
      return (
        <li className="nav-item" key={form}>
          <button
            className={ "mr-2 nav-link btn " + (activeForm === form ? "active" : "btn-info") } onClick={this.onGeneralInfoBtnClick}>
            {form}
          </button>
        </li>
      )
    })
  }

  render() {
    const activeForm = this.props.activeForm
    console.log('active form ', activeForm)
    return (
      <div className="container-fluid">
        <div className="mt-4">
          <div className="nav justify-content-between">
            <div/>
            <ul className="nav nav-pills">
              {this.renderList(activeForm)}
            </ul>
          </div>
        </div>
      </div>
    )
  }
}
