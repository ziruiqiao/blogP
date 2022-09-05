export function formatTime(time) {
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return 'just now'
  } else if (diff < 3600) { // less 1 hour
    return Math.ceil(diff / 60) + 'mins ago'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + 'hours ago'
  } else if (diff < 3600 * 24 * 2) {
    return '1 day ago'
  }

  return time
}