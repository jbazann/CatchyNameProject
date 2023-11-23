export const FORMAT_VALIDATION_REGEX = '<69>(<noop>|<append>|<full-replace>|<insert>[0-9]+<\/insert>|<partial-replace>[0-9]+-[0-9]+<\/partial-replace>)(.|\n)*<\/69>'
const INDEX_PARSING_REGEX = new RegExp('[0-9]+','g'); // I'd love to know why I can't add /.../g with just strings
const INSERT_TAG_PARSING_REGEX = '<insert>[0-9]+<\/insert>';
const PARTIAL_REPLACE_TAG_PARSING_REGEX = '<partial-replace>[0-9]+-[0-9]+<\/partial-replace>';

const GLOBAL_DELIMITER_OPEN = '<69>';
const GLOBAL_DELIMITER_CLOSE = '</69>';

export const APPEND = '<append>';
export const FULL_REPLACE = '<full-replace>';
export const INSERT_OPEN = '<insert>';
export const PARTIAL_REPLACE_OPEN = '<partial-replace>';
export const NO_OP = '<noop>';

const INSERT_CLOSE = '</insert>';
const PARTIAL_REPLACE_CLOSE = '</partial-replace>';

export function append(str) {
    return GLOBAL_DELIMITER_OPEN+APPEND+str+GLOBAL_DELIMITER_CLOSE;
}

export function fullReplace(srt) {
    return GLOBAL_DELIMITER_OPEN+FULL_REPLACE+srt+GLOBAL_DELIMITER_CLOSE;
}

export function insert(str,at) {
    return GLOBAL_DELIMITER_OPEN+INSERT_OPEN+at.toString()+INSERT_CLOSE+str+GLOBAL_DELIMITER_CLOSE;
}

export function partialReplace(str,from,to) {
    return GLOBAL_DELIMITER_OPEN+PARTIAL_REPLACE_OPEN+from.toString()+'-'+to.toString()+PARTIAL_REPLACE_CLOSE+GLOBAL_DELIMITER_CLOSE;
}

export function noOp() {
    return GLOBAL_DELIMITER_OPEN+NO_OP+GLOBAL_DELIMITER_CLOSE;
}

export function getOperation(str) {
    const validation = str.match(FORMAT_VALIDATION_REGEX);
    if(!validation) return false;
    return parseOperation(validation[0]);
}

// obviously this assumes a valid string dont b silly
function parseOperation(str) {
    let asd = 'asd';
    asd.match()
    const trimmed = str.replace(GLOBAL_DELIMITER_OPEN,'').replace(GLOBAL_DELIMITER_CLOSE,'');
    if(trimmed.startsWith(APPEND)) return new TextareaOperation(trimmed.replace(APPEND,''),APPEND,null);
    if(trimmed.startsWith(FULL_REPLACE)) return new TextareaOperation(trimmed.replace(FULL_REPLACE,''),FULL_REPLACE,null);
    if(trimmed.startsWith(INSERT_OPEN)) {
        const tag = trimmed.match(INSERT_TAG_PARSING_REGEX)[0];
        const index = tag.match(INDEX_PARSING_REGEX);
        return new TextareaOperation(trimmed.replace(tag,''),INSERT_OPEN,[parseInt(index[0])]);
    }
    if(trimmed.startsWith(PARTIAL_REPLACE_OPEN)) {
        const tag = trimmed.match(PARTIAL_REPLACE_TAG_PARSING_REGEX)[0];
        const index = tag.match(INDEX_PARSING_REGEX);
        return new TextareaOperation(trimmed.replace(tag,''),PARTIAL_REPLACE_OPEN,[parseInt(index[0]),parseInt(index[1])]);
    }
    if(trimmed.startsWith(NO_OP)) return new TextareaOperation(trimmed.replace(NO_OP,''),NO_OP,null)
    return null;
}

export class TextareaOperation {
    constructor (content,operation,metadata) {
        this.content = content;
        this.operation = operation;
        this.metadata = metadata;
    }

    toString() {
        return `o:${this.operation} c:${this.content} m:${this.metadata}`;
    }
}