import React, { Component } from "react"
import _ from "lodash"
import DocumentRow from "./document_row"

const documents = [
  {
    type: DIPLOMA_CERTIFICATE,
    text: 'Certificate of secondary education completion or the original diploma of technical and vocational (primary or secondary vocational, post-secondary) education'
  },
  {
    type: UNT_CT_CERTIFICATE,
    text: 'Certificate of UNT / CT'
  },
  {
    type: PHOTO_3x4,
    text: 'Photo 3x4',
  },
  {
    type: HEALTH_086,
    text: 'Health certificate 086-U'
  },
  {
    type: HEALTH_063,
    text: 'Health certificate 0-63'
  },
  {
    type: FLUOROGRAPHY,
    text: 'fluorography'
  },
  {
    type: IDENTITY_CARD_FRONT,
    text: 'A copy of the identity card (front side)'
  },
  {
    type: IDENTITY_CARD_BACK,
    text: 'A copy of the identity card (back side)'
  }
]

import {
  DIPLOMA_CERTIFICATE,
  UNT_CT_CERTIFICATE,
  PHOTO_3x4,
  HEALTH_086,
  HEALTH_063,
  FLUOROGRAPHY,
  IDENTITY_CARD_FRONT,
  IDENTITY_CARD_BACK,

  WAITING_FOR_RESPONSE,
  ACCEPTED,
  REJECTED,
  NOT_SEND

} from "../../constants"

import config from "../../config"

class EditStudentDocuments extends Component {

  constructor(props) {
    super(props)

    this.state = {
      documentsComment: this.props.selectedStudent.documentsComment
    }
  }

  renderDocuments(userDocuments) {
    return _.map(
      documents, document => {

        const path = `${config.url}/upload/${userDocuments[document.type].url}`
        const status = userDocuments[document.type].status
        const id = userDocuments[document.type].id
        const title = document.text

        const onDocumentStatusChange = this.props.onDocumentStatusChange

        return (
          <DocumentRow key={ document.type }
                       path={ path }
                       status={ status }
                       title={ title }
                       id={ id }
                       onDocumentStatusChange={ onDocumentStatusChange }
                       document={ userDocuments[document.type] }
          />
        )
      }
    )
  }

  handleTextAreaChange = (event) => {
    this.setState({ documentsComment: event.target.value })
  }

  handleSaveCommentBtn = () => {
    this.props.onSaveDocumentsComment(this.state.documentsComment )
  }

  render() {
    const comment = this.state.documentsComment

    const documents = _.mapKeys(this.props.selectedStudent.documents, "type")

    const btnClass = "btn " + (this.props.selectedStudent.documentsComment === comment ? "btn-info " : "btn-outline-info")

    return (
      <div>
        <h3>Documents</h3>
        <ol className="list-group">
          {this.renderDocuments(documents)}
        </ol>
        <div className="form-group">
          <label htmlFor="documentsComment"><strong>Leave comment</strong></label>
          <textarea className="form-control" id="documentsComment" rows="3" placeholder="Leave comment..."
                    defaultValue={ comment } onChange={this.handleTextAreaChange.bind(this)}/>
        </div>
        <div>
          <button className={btnClass} onClick={this.handleSaveCommentBtn}>Save comment for documents</button>
        </div>
      </div>
    )
  }
}

export default EditStudentDocuments