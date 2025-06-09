/**
 * 判断是否是外部链接
 * @param path 路径
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 判断是否是URL
 * @param url URL
 */
export function isURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * 判断是否是邮箱
 * @param email 邮箱
 */
export function isEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * 判断是否是手机号
 * @param phone 手机号
 */
export function isPhone(phone) {
  const reg = /^1[3-9]\d{9}$/
  return reg.test(phone)
}

/**
 * 判断是否是身份证号
 * @param idCard 身份证号
 */
export function isIdCard(idCard) {
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  return reg.test(idCard)
}

/**
 * 判断是否是IP地址
 * @param ip IP地址
 */
export function isIP(ip) {
  const reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
  return reg.test(ip)
}

/**
 * 判断是否是端口号
 * @param port 端口号
 */
export function isPort(port) {
  const reg = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/
  return reg.test(port)
}

/**
 * 判断是否是密码
 * @param password 密码
 */
export function isPassword(password) {
  const reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/
  return reg.test(password)
}

/**
 * 判断是否是用户名
 * @param username 用户名
 */
export function isUsername(username) {
  const reg = /^[a-zA-Z0-9_-]{4,16}$/
  return reg.test(username)
}

/**
 * 判断是否是中文
 * @param str 字符串
 */
export function isChinese(str) {
  const reg = /^[\u4e00-\u9fa5]+$/
  return reg.test(str)
}

/**
 * 判断是否是英文
 * @param str 字符串
 */
export function isEnglish(str) {
  const reg = /^[a-zA-Z]+$/
  return reg.test(str)
}

/**
 * 判断是否是数字
 * @param str 字符串
 */
export function isNumber(str) {
  const reg = /^[0-9]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母和数字
 * @param str 字符串
 */
export function isAlphanumeric(str) {
  const reg = /^[a-zA-Z0-9]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母、数字和下划线
 * @param str 字符串
 */
export function isAlphanumericAndUnderscore(str) {
  const reg = /^[a-zA-Z0-9_]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母、数字、下划线、横线
 * @param str 字符串
 */
export function isAlphanumericAndUnderscoreAndHyphen(str) {
  const reg = /^[a-zA-Z0-9_-]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母、数字、下划线、横线、点
 * @param str 字符串
 */
export function isAlphanumericAndUnderscoreAndHyphenAndDot(str) {
  const reg = /^[a-zA-Z0-9_.-]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母、数字、下划线、横线、点、空格
 * @param str 字符串
 */
export function isAlphanumericAndUnderscoreAndHyphenAndDotAndSpace(str) {
  const reg = /^[a-zA-Z0-9_. -]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母、数字、下划线、横线、点、空格、中文
 * @param str 字符串
 */
export function isAlphanumericAndUnderscoreAndHyphenAndDotAndSpaceAndChinese(str) {
  const reg = /^[a-zA-Z0-9_. \u4e00-\u9fa5-]+$/
  return reg.test(str)
}

/**
 * 判断是否是字母、数字、下划线、横线、点、空格、中文和特殊字符
 * @param str 字符串
 */
export function isAlphanumericAndUnderscoreAndHyphenAndDotAndSpaceAndChineseAndSpecial(str) {
  const reg = /^[a-zA-Z0-9_. \u4e00-\u9fa5!@#$%^&*()_+\[\]{}\\;:'"\|,.<>\/?-]+$/
  return reg.test(str)
}