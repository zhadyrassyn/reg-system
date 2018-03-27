import React, { Component } from "react"

import OverviewMenu from "./overview_menu"
import GeneralInfoForm from "./general_info_form"
import DocumentsForm from "./documents_form"

import {
  FORM_GENERAL,
  FORM_DOCUMENTS
} from "../../constants"

class StudentApp extends Component {
  constructor(props) {
    super(props)

    this.state = {
      activeForm: FORM_GENERAL
    }
  }

  changeForm = (formType) => {
    this.setState({ activeForm: formType })
  }

  render() {
    const activeForm = this.state.activeForm

    return (
      <div>
        <OverviewMenu activeForm={activeForm} changeForm={this.changeForm.bind(this)}/>
        { activeForm === FORM_GENERAL && <GeneralInfoForm /> }
        { activeForm === FORM_DOCUMENTS && <DocumentsForm /> }
      </div>
    )
  }
}

export default StudentApp