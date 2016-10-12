
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-body">

            <form role="form" name="editPay" id="editPay" action="">
                <fieldset>
                    <div class="col-lg-10 col-md-10">

                        <div class="form-group col-lg-5 col-sm-5" id="categoryControlGroup">
                            <label class="control-label"> Category </label>
                            <div class="controls">
                                <input class="form-control" type="text" id="category" name="category" value="" />
                            </div>
                        </div>

                        <div class="form-group col-lg-3 col-sm-3" id="sumControlGroup">
                            <label class="control-label"> Sum </label>
                            <div class="controls">
                                <input class="form-control" id="sum" name="sum" type="number"" value="" />
                            </div>
                        </div>

                        <div class="form-group col-lg-2 col-sm-2" id="quantityControlGroup">
                            <label class="control-label"> Quantity </label>
                            <div class="controls">
                                <input class="form-control" id="quantity" name="quantity" type="number" value="" />
                            </div>
                        </div>

                        <div class="form-group col-lg-2 col-sm-2" id="priceControlGroup">
                            <label class="control-label"> Price </label>
                            <div class="controls">
                                <input class="form-control" id="price" name="price" type="number" value="" />
                            </div>
                        </div>

                        <div class="form-group col-lg-3 col-sm-3" id="tagControlGroup">
                            <label class="control-label"> Tag </label>
                            <div class="controls">
                                <select class="form-control" data-width="auto" id="tag" name="tag" >
                                    <option value="0">Select tag</option>

                                </select>
                            </div>
                        </div>

                        <div class="form-group col-lg-4 col-sm-4" id="personControlGroup">
                            <label class="control-label"> Person </label>
                            <div class="controls">
                                <select class="form-control" data-width="auto" id="person" name="person" >
                                    <option value="0">Select person</option>

                                </select>
                            </div>
                        </div>

                        <div class="form-group col-lg-5 col-sm-5" id="commentsControlGroup">
                            <label class="control-label"> Comments </label>
                            <div class="controls">
                                <input class="form-control" id="comments" name="comments" type="text" value="" />
                            </div>
                        </div>

                    </div>
                    <div class="col-lg-2 col-md-2">
                        <label class="control-label">  </label>
                        <div class="controls">
                            <button form="editPay" type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </fieldset>
            </form>

        </div>

        <div class="panel-body">

        </div>
    </div>
</div>
