import React, { Component } from "react"
import EditStudentDocuments from "./edit_student_documents"
import EditStudentGeneralInfo from "./edit_student_general_Info"

import {
  FORM_GENERAL,
  FORM_DOCUMENTS
} from "../../constants"



class EditStudent extends Component {
  constructor(props) {
    super(props)

    this.state = {
      activeForm: FORM_GENERAL,
    }
  }

  changeForm = () => {
    this.setState({activeForm: this.state.activeForm === FORM_GENERAL ? FORM_DOCUMENTS : FORM_GENERAL})
  }

  render() {
    const { closeModal, selectedStudent, onGeneralInfoEdit, onSaveDocumentsComment, onDocumentStatusChange } = this.props
    console.log('selectedStudent ', selectedStudent)

    const documents = _.mapKeys(selectedStudent.documents, "type")
    const activeForm = this.state.activeForm
    return (
      <div className="container">
        <button onClick={closeModal} className="close" type="button"/>
        <div className="mt-4">
          <div className="nav justify-content-between">
            <ul className="nav nav-pills">
              <li className="nav-item">
                <button
                  className={ "mr-2 nav-link btn " + (activeForm === FORM_GENERAL ? "active" : "") } onClick={this.changeForm}>
                  General info
                </button>
              </li>
              <li className="nav-item">
                <button className={ "nav-link btn " + (activeForm === FORM_DOCUMENTS ? "active" : "") } onClick={this.changeForm}>
                  Documents
                </button>
              </li>
            </ul>
          </div>
        </div>

        <div className="row mt-3">
          <div className="col-md-12">
            {activeForm === FORM_GENERAL &&
              <EditStudentGeneralInfo
                selectedStudent={ selectedStudent }
                onGeneralInfoEdit={ onGeneralInfoEdit }
              />
            }
            {activeForm === FORM_DOCUMENTS &&
              <EditStudentDocuments
                selectedStudent={ selectedStudent }
                onSaveDocumentsComment={ onSaveDocumentsComment }
                onDocumentStatusChange = { onDocumentStatusChange }
              />
            }
          </div>
        </div>
      </div>
    )
  }
}

export default EditStudent