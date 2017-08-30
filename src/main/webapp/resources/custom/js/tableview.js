/* =========================================================
 * tableview.js v1.0.0
 * =========================================================
 * Copyright 2017 SpecialQ
 * 
 * ========================================================= */
;(function($){
	
	'use strict';
	
	var pluginName = 'customTable';
	
	var _default = {}
	
	_default.settings = {
        striped: false,
        sortName: '0',
        pagination: true,
        pageNumber: 1,
        pageSize: 5,
        sortStable: true,
        iconsPrefix: 'glyphicon glyphicon-user',
        selectItemName: 'id',
        search: true,
        searchOnEnterKey: false,
        strictSearch: false,
        showHeader: true,
        showFooter: false,
        showColumns: true,
        showRefresh: true,
        showToggle: true,
        showPaginationSwitch: false,
        trimOnSearch: false,
        paginationLoop: true,
        minimumCountColumns: 1,
        idField: 'id',
        uniqueId: 'username',
        escape: true,
        searchAlign: 'right',
        buttonsAlign: 'left',
        paginationVAlign: 'bottom',
        paginationHAlign: 'right',
        paginationDetailHAlign: 'left',
        
        showExport: true,
        exportDataType: 'all',
        exportTypes: ['json', 'xml', 'csv', 'txt', 'xlsx'],
        exportOptions: {fileName: 'mini-finance.datas'}
	}
	
	$.fn[pluginName] = function (headers, datas) {
		_default.settings.columns = headers;
		_default.settings.data = datas;
		this.bootstrapTable(_default.settings);
	};
})(jQuery)