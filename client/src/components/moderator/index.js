import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import SearchBar from "./search_bar"
import TableHeader from "./table_header"
import TableBody from "./table_body"
import Pagination from "./pagination"
import EditStudent from "./edit_student"

import Modal from "react-modal"

const customStyles = {
  content: {
    width: "100%",
    height: "100%",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0
  }
}

Modal.setAppElement(".root")

import {
  fetchStudents,
  fetchStudentFullInfo,
  editGeneralInfo,
  saveDocumentsComment,
  changeDocumentStatus,
  fetchTotalAmountOfStudents
} from "../../actions"

class ModeratorApp extends Component {

  constructor(props) {
    super(props)

    this.state = {
      search: "",
      currentPage: 1,
      perPage: 10,
      modalIsOpen: false
    }

    this.openModal = this.openModal.bind(this);
    this.afterOpenModal = this.afterOpenModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  openModal(id) {

    this.props.fetchStudentFullInfo(
      id,
      () => {
        console.log('success on fetch student full info')
        this.setState({modalIsOpen: true});
      },
      () => {
        console.log('fail on fetch student full info')
      }
    )
  }

  afterOpenModal() {
    // references are now sync'd and can be accessed.
    console.log("true modal")
    document.body.style.overflow = "hidden";
  }

  closeModal() {
    this.setState({modalIsOpen: false});
    document.body.style.overflow = "auto";
  }


  componentWillMount() {
    document.body.style.backgroundColor = "#F8F6F9";
  }

  componentWillUnmount() {
    document.body.style.backgroundColor = null;
  }

  componentDidMount() {
    const {currentPage, perPage, search} = this.state
    this.props.fetchTotalAmountOfStudents(search,
      () => {
        this.props.fetchStudents(search, currentPage, perPage)
      },
      () => {
        console.log('error on fetching students')
      })

  }

  handlePageChangeClick = (pageNum) => {
    const {search} = this.state
    this.props.fetchStudents(search, pageNum, this.state.perPage,
      () => {
        this.setState({currentPage: pageNum})
      })
  }

  handleSearch = (search) => {
    this.props.fetchTotalAmountOfStudents(search,
      () => {
        this.props.fetchStudents(search, 1, this.state.perPage,
          () => {
            console.log('success')
            this.setState({currentPage: 1, search})
          })
      },
      () => {
        console.log('error on fetching students')
      })

  }

  saveDocumentsComment = (comment) => {
    this.props.saveDocumentsComment(this.props.selectedStudent.id, comment,
      () => {
        console.log('123')
      },
      () => {
        console.log('456')
      })
  }

  onGeneralInfoEdit = (generalInfoComment, generalInfoStatus) => {
    this.props.editGeneralInfo(this.props.selectedStudent.id, generalInfoComment, generalInfoStatus,
      () => {
        console.log('onSuccess')
      },
      () => {
        console.log('onError')
      })
  }

  changeDocumentStatus = (document, successCallback, errorCallback) => {

    this.props.changeDocumentStatus(
      this.props.selectedStudent.id,
      document,
      successCallback,
      errorCallback
    )
  }

  render() {
    const {students, selectedStudent, total} = this.props
    const {currentPage, perPage} = this.state
    const startCounter = (currentPage - 1) * perPage
    console.log(students)
    console.log(total)

    return (
      <div className="wrapper">
        <SearchBar onSearch={this.handleSearch}/>
        <TableHeader/>
        <TableBody students={students} startCounter={startCounter} openModal={this.openModal.bind(this)}/>
        <Pagination currentPage={currentPage} perPage={perPage} total={total}
                    handlePageChangeClick={this.handlePageChangeClick}/>
        <Modal
          isOpen={this.state.modalIsOpen}
          onAfterOpen={this.afterOpenModal}
          onRequestClose={this.closeModal}
          style={customStyles}
          contentLabel="Example Modal">
          <EditStudent closeModal={this.closeModal}
                       selectedStudent={selectedStudent}
                       onGeneralInfoEdit={this.onGeneralInfoEdit}
                       onSaveDocumentsComment={this.saveDocumentsComment}
                       onDocumentStatusChange={this.changeDocumentStatus}
          />
        </Modal>
      </div>
    )
  }
}

export default connect(
  state => ({
    students: state.moderator.students,
    selectedStudent: state.moderator.selectedStudent,
    total: state.moderator.total
  }),
  dispatch => ({
    fetchStudents: bindActionCreators(fetchStudents, dispatch),
    fetchStudentFullInfo: bindActionCreators(fetchStudentFullInfo, dispatch),
    editGeneralInfo: bindActionCreators(editGeneralInfo, dispatch),
    saveDocumentsComment: bindActionCreators(saveDocumentsComment, dispatch),
    changeDocumentStatus: bindActionCreators(changeDocumentStatus, dispatch),
    fetchTotalAmountOfStudents: bindActionCreators(fetchTotalAmountOfStudents, dispatch)
  })
)(ModeratorApp)