import React, { Component } from "react"

class PaginationItem extends Component {
  constructor(props) {
    super(props)

  }

  handlePageChange(pageNum) {
    this.props.pageChangeClicked(pageNum)
  }

  render() {
    const pageNum = this.props.value
    const currentPage = this.props.currentPage

    return (
      <li onClick={this.handlePageChange.bind(this, pageNum)}
          className={pageNum === currentPage ? "page-item active" : "page-ite"}><a className='page-link' href="#">{pageNum}</a></li>
    )
  }

}

export default PaginationItem